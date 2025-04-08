package server.photo.domain.room.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.invitation.repository.InvitationRepository;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.join.repository.JoinRequestRepository;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.room.converter.RoomConverter;
import server.photo.domain.room.dto.request.RoomCreateRequest;
import server.photo.domain.room.dto.request.RoomUpdateRequest;
import server.photo.domain.room.dto.response.*;
import server.photo.domain.room.entity.Room;
import server.photo.domain.room.repository.RoomRepository;
import server.photo.domain.room.repository.RoomRepositoryImpl;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.response.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import server.photo.global.s3.S3FileService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final S3FileService s3FileService;
    private final JoinRequestRepository joinRequestRepository;
    private final InvitationRepository invitationRepository;
    private final RoomRepositoryImpl roomRepositoryImpl;

    //방 생성
    @Transactional
    public BaseResponse<RoomCreateResponse> createRoom(RoomCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        Room room = RoomConverter.toRoom(request, user);

        if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            MultipartFile thumbnail = request.getThumbnail();
            String imageUrl = s3FileService.uploadFile(thumbnail);
            room.updateThumbnailImage(imageUrl);
        }

        room.addRoomMember(user);
        room = roomRepository.save(room);

        RoomCreateResponse roomResponse = RoomConverter.toRoomCreateResponse(room);

        return BaseResponse.success(roomResponse);
    }

    // 방 정보 수정
    @Transactional
    public BaseResponse<Object> updateRoom(Long roomId, RoomUpdateRequest request) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));

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

        return BaseResponse.success();
    }

    //방 리스트 조회
    public BaseResponse<RoomListDto> getRooms(String q, String condition, String order) {

        List<Room> rooms;

        if ("room".equals(condition)) {
            rooms = getRoomsByRoomName(q, order);
        } else if ("host".equals(condition)) {
            rooms = getRoomsByHostNickname(q, order);
        } else if(StringUtils.hasText(condition)){
            throw new BaseException(BaseResponseStatus.BAD_REQUEST_CONDITION);
        } else{
            rooms = getRoomsByCreatedAt(order);
        }

        List<RoomListResponse> roomListResponses = new ArrayList<>();
        for (Room room : rooms) {
            User host = userRepository.findById(room.getHostId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
            RoomMetadata roomMetadata = RoomConverter.toRoomMetadata(room, host);
            RoomListDetail roomDetail = RoomConverter.toRoomListDetail(room);
            RoomListResponse roomListResponse = RoomConverter.toRoomListResponse(roomMetadata, roomDetail);
            roomListResponses.add(roomListResponse);
        }
        RoomListDto roomListDto = RoomConverter.toRoomListDto(roomListResponses);

        return BaseResponse.success(roomListDto);
    }

    private List<Room> getRoomsByCreatedAt(String order) {
        if("date_asc".equals(order)){
            return roomRepositoryImpl.findRoomsByCreatedAtAsc();
        } else if("date_desc".equals(order)){
            return roomRepositoryImpl.findRoomsByCreatedAtDesc();
        } else if(StringUtils.hasText(order)){
            throw new BaseException(BaseResponseStatus.BAD_REQUEST_ORDER);
        }
        return roomRepositoryImpl.findRoomsByCreatedAtDesc();
    }

    //방 제목 기준으로 검색
    private List<Room> getRoomsByRoomName(String roomName, String order) {
        if ("date_asc".equals(order)) {
            return roomRepositoryImpl.findRoomsByRoomNameAsc(roomName);
        } else if("date_desc".equals(order)) {
            return roomRepositoryImpl.findRoomsByRoomNameDesc(roomName);
        } else if(StringUtils.hasText(order)){
            throw new BaseException(BaseResponseStatus.BAD_REQUEST_ORDER);
        }
        return roomRepositoryImpl.findRoomsByRoomNameDesc(roomName);
    }

    //호스트 닉네임 기준으로 검색
    private List<Room> getRoomsByHostNickname(String hostNickname, String order) {
        if ("date_asc".equals(order)) {
            return roomRepositoryImpl.findRoomsByHostNicknameAsc(hostNickname);
        } else if("date_desc".equals(order)) {
            return roomRepositoryImpl.findRoomsByHostNicknameDesc(hostNickname);
        } else if(StringUtils.hasText(order)){
            throw new BaseException(BaseResponseStatus.BAD_REQUEST_ORDER);
        }
        return roomRepositoryImpl.findRoomsByHostNicknameDesc(hostNickname);
    }

    //특정 방 입장
    public BaseResponse<Object> enterRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
        User host = userRepository.findById(room.getHostId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        Boolean isMember = room.getRoomMembers().stream().anyMatch(member -> member.getUser().getId().equals(userId));
        Boolean isHost = room.getHostId().equals(userId);

        if (!isMember) {
            Boolean isJoinRequestSent = joinRequestRepository.existsByRoomAndUserId(room, user.getId());
            Long sentJoinRequestId = null;
            if(isJoinRequestSent){
                JoinRequest joinRequest = joinRequestRepository.findByUserIdAndRoom(userId, room);
                sentJoinRequestId = joinRequest.getId();
            }

            UserContextFail userContext = RoomConverter.toUserContextFail(isMember, sentJoinRequestId, isHost);
            RoomMetadataFail roomMetadata = RoomConverter.toRoomMetadataFail(room, host);
            RoomDetail roomDetail = RoomConverter.toRoomDetail(room);

            RoomEnterFailResponse roomEnterFailResponse = RoomConverter.toRoomEnterFailResponse(userContext, roomMetadata, roomDetail);

            return BaseResponse.success(roomEnterFailResponse);
        }

        List<MemberInfo> members = room.getRoomMembers().stream()
                .map(member -> MemberInfo.builder()
                        .userId(member.getUser().getId())
                        .nickname(member.getUser().getNickname())
                        .build())
                .collect(Collectors.toList());

        List<MomentInfo> moments = room.getMoments().stream()
                .sorted(Comparator.comparing(Moment::getCreatedAt).reversed())
                .map(moment -> MomentInfo.builder()
                        .momentId(moment.getId())
                        .momentImage(moment.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        Long numOfNewJoinRequests = joinRequestRepository.countByRoom(room);

        UserContextSuccess userContext = RoomConverter.toUserContextSuccess(isMember, isHost);
        RoomMetadataSuccess roomMetadata = RoomConverter.toRoomMetadataSuccess(room, host);
        RoomDetail roomDetail = RoomConverter.toRoomDetail(room);
        RoomPrivate roomPrivate = RoomConverter.toRoomPrivate(members, moments, numOfNewJoinRequests);

        RoomEnterSuccessResponse roomEnterSuccessResponse = RoomConverter.toRoomEnterSuccessResponse(userContext, roomMetadata, roomDetail, roomPrivate);
        return BaseResponse.success(roomEnterSuccessResponse);
    }

    //방 삭제
    @Transactional
    public BaseResponse<Object> deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));
        roomRepository.delete(room);
        return BaseResponse.success();
    }

    //방 나가기
    @Transactional
    public BaseResponse<Object> leaveRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        room.removeRoomMember(user);
        
        return BaseResponse.success();
    }

    // 방에서 보낸 초대 요청 목록 조회
    public BaseResponse<RoomInvitationsDto> getInvitations(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));

        List<Invitation> invitations = invitationRepository.findByRoom(room);

        List<RoomInvitationList> invitationLists = new ArrayList<>();
        for (Invitation invitation : invitations) {
            User inviter = invitation.getUser();
            RoomInvitationList invitationList = RoomConverter.toRoomInvitationList(invitation, inviter);
            invitationLists.add(invitationList);
        }

        RoomInvitationsDto roomInvitationsDto = RoomConverter.toRoomInvitationsDto(invitationLists);

        return BaseResponse.success(roomInvitationsDto);
    }

    //방 참여 요청 목록
    public BaseResponse<JoinRequestListResponse> getJoinRequests(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));

        List<JoinRequest> joinRequests = joinRequestRepository.findByRoom(room);
        List<JoinRequestDetail> joinList = new ArrayList<>();

        for (JoinRequest joinRequest : joinRequests) {
            User user = joinRequest.getUser();
            JoinRequestDetail joinRequestDetail = RoomConverter.toJoinRequestDetail(joinRequest, user);
            joinList.add(joinRequestDetail);
        }

        JoinRequestListResponse joinResponseDto = RoomConverter.toJoinRequestListResponse(joinList);

        return BaseResponse.success(joinResponseDto);
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
