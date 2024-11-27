package hello.photo.domain.room.service;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.join.repository.JoinRequestRepository;
import hello.photo.domain.room.converter.RoomConverter;
import hello.photo.domain.room.dto.request.RoomCreateRequest;
import hello.photo.domain.room.dto.request.RoomUpdateRequest;
import hello.photo.domain.room.dto.response.*;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponseDto;
import hello.photo.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final S3FileService s3FileService;
    private final JoinRequestRepository joinRequestRepository;
    private final InvitationRepository invitationRepository;

    //방 생성
    @Transactional
    public DataResponseDto<RoomCreateResponse> createRoom(RoomCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        Room room = RoomConverter.toRoom(request);

        if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            MultipartFile thumbnail = request.getThumbnail();
            String imageUrl = s3FileService.uploadFile(thumbnail);

            room.updateThumbnailImage(imageUrl);
        }

        room.addRoomMember(user);
        room = roomRepository.save(room);

        RoomCreateResponse roomResponse = RoomConverter.toRoomCreateResponse(room);

        return DataResponseDto.of(roomResponse, Code.OK.getMessage());
    }

    // 방 정보 수정
    @Transactional
    public ApiResponse updateRoom(Long roomId, RoomUpdateRequest request) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        if(request.getRoomName() != null){
            room.updateRoomName(request.getRoomName());
        }

        if(request.getRoomDescription() != null){
            room.updateRoomDescription(request.getRoomDescription());
        }

        if (request.getThumbnail() == null || request.getThumbnail().isEmpty()) {
            if (room.getThumbnailImage() != null) {
                String existingFileName = extractFileNameFromUrl(room.getThumbnailImage());
                s3FileService.deleteFile(existingFileName);
                room.updateThumbnailImage(null);
            }
        } else {
            if (room.getThumbnailImage() != null) {
                String existingFileName = extractFileNameFromUrl(room.getThumbnailImage());
                s3FileService.deleteFile(existingFileName);
            }

            MultipartFile thumbnail = request.getThumbnail();
            String imageUrl = s3FileService.uploadFile(thumbnail);

            room.updateThumbnailImage(imageUrl);
        }

        return ApiResponse.of(Code.OK.getMessage());
    }

    //방 리스트 조회
    public DataResponseDto<RoomListResponse> getRooms(String order, String condition, String q) {
        Sort sort;
        if (order.equals("date_desc")) {
            sort = Sort.by(Sort.Direction.DESC, "createdAt");
        } else if (order.equals("date_asc")) {
            sort = Sort.by(Sort.Direction.ASC, "createdAt");
        } else {
            sort = Sort.unsorted();
        }

        List<Room> rooms;
        if (q != null && !q.isEmpty()) {
            if ("room".equals(condition)) {
                rooms = roomRepository.findByRoomNameContaining(q, sort);
            } else if ("host".equals(condition)) {
                rooms = roomRepository.findByUserNicknameContaining(q, sort);
            } else {
                rooms = roomRepository.findAll(sort);
            }
        } else {
            rooms = roomRepository.findAll(sort);
        }

        List<RoomListInfo> roomListInfos = new ArrayList<>();
        for (Room room : rooms) {
            User host = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
            RoomListInfo roomListInfo = RoomConverter.toRoomListInfo(room, host.getNickname());
            roomListInfos.add(roomListInfo);
        }

        RoomListResponse roomListResponse = RoomConverter.toRoomListResponse(roomListInfos);

        return DataResponseDto.of(roomListResponse, Code.OK.getMessage());
    }

    //특정 방 입장
    public ApiResponse enterRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User host = userRepository.findById(room.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        Boolean isMember = room.getRoomMembers().stream().anyMatch(member -> member.getUser().getId().equals(userId));

        String thumbnailUrl = room.getThumbnailImage();

        if (!isMember) {
            Boolean isJoinRequestSent = joinRequestRepository.existsByRoomAndUserId(room, user.getId());
            RoomEnterFailResponse roomEnterFailResponse = RoomEnterFailResponse.builder()
                    .isMember(false)
                    .roomId(room.getId())
                    .roomName(room.getRoomName())
                    .roomDescription(room.getRoomDescription())
                    .hostName(host.getNickname())
                    .thumbnail(thumbnailUrl)
                    .isJoinRequestSent(isJoinRequestSent)
                    .createdAt(room.getCreatedAt())
                    .build();

            return DataResponseDto.of(roomEnterFailResponse,"해당 회원은 현재 이 Room의 Member로 등록되어 있지 않습니다");
        }

        List<MemberInfo> members = room.getRoomMembers().stream()
                .map(member -> MemberInfo.builder()
                        .userId(member.getUser().getId())
                        .nickname(member.getUser().getNickname())
                        .build())
                .collect(Collectors.toList());

        List<MomentEnterInfo> moments = room.getMoments().stream()
                .map(moment -> MomentEnterInfo.builder()
                        .momentId(moment.getId())
                        .imageUrl(moment.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        Long numOfNewJoinRequests = joinRequestRepository.countByRoom(room);

        List<Invitation> invitations = invitationRepository.findByRoom(room);
        List<String> invitedUsers = new ArrayList<>();

        for (Invitation invitation : invitations) {
            User invitaionUser = userRepository.findById(invitation.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
            String nickname = invitaionUser.getNickname();
            invitedUsers.add(nickname);
        }

        RoomEnterInfo roomEnterInfo = RoomEnterInfo.builder()
                .members(members)
                .moments(moments)
                .numOfNewJoinRequests(numOfNewJoinRequests)
                .invitedUsers(invitedUsers)
                .build();

        RoomEnterSuccessResponse roomEnterSuccessResponse = RoomEnterSuccessResponse.builder()
                .isMember(true)
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .roomDescription(room.getRoomDescription())
                .hostName(host.getNickname())
                .roomDetail(roomEnterInfo)
                .thumbnail(thumbnailUrl)
                .createdAt(room.getCreatedAt())
                .userId(room.getUserId())
                .build();

        return DataResponseDto.of(roomEnterSuccessResponse, Code.OK.getMessage());
    }

    //방 삭제
    @Transactional
    public ApiResponse deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        roomRepository.delete(room);
        return ApiResponse.of(Code.OK.getMessage());
    }

    //방 탈퇴
    @Transactional
    public ApiResponse leaveRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        room.removeRoomMember(user);
        
        return ApiResponse.of(Code.OK.getMessage());
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
