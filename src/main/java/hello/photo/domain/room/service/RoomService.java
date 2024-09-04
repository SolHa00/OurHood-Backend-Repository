package hello.photo.domain.room.service;

import hello.photo.domain.join.repository.JoinRequestRepository;
import hello.photo.domain.room.dto.request.RoomCreateRequest;
import hello.photo.domain.room.dto.response.*;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.entity.Thumbnail;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.room.repository.ThumbnailRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final S3FileService s3FileService;
    private final ThumbnailRepository thumbnailRepository;
    private final JoinRequestRepository joinRequestRepository;

    //방 생성
    public DataResponseDto<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        Room room = Room.builder()
                .roomName(request.getRoomName())
                .roomDescription(request.getRoomDescription())
                .host(user)
                .build();
        room.getMembers().add(user);
        room = roomRepository.save(room);

        //썸네일이 있는 경우에만 처리
        String imageUrl = null;
        if(request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            MultipartFile thumbnail = request.getThumbnail();
            imageUrl = s3FileService.uploadFile(thumbnail);

            Thumbnail thumbnailImage = Thumbnail.builder()
                    .thumbnailUrl(imageUrl)
                    .user(user)
                    .room(room)
                    .build();
            thumbnailRepository.save(thumbnailImage);

            room.setThumbnail(thumbnailImage);
            roomRepository.save(room);
        }

        RoomCreateResponse roomResponse = RoomCreateResponse.builder()
                .roomId(room.getId())
                .build();

        return DataResponseDto.of(roomResponse, Code.OK.getMessage());
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
                rooms = roomRepository.findByHostNicknameContaining(q, sort);
            } else {
                rooms = roomRepository.findAll(sort);
            }
        } else {
            rooms = roomRepository.findAll(sort);
        }

        List<RoomListInfo> roomListInfos = rooms.stream()
                .map(room -> {
                    // 썸네일 URL 가져오기
                    String thumbnailUrl = null;
                    Optional<Thumbnail> thumbnail = thumbnailRepository.findByRoom(room);
                    if (thumbnail.isPresent()) {
                        thumbnailUrl = thumbnail.get().getThumbnailUrl();
                    }

                    return RoomListInfo.builder()
                            .roomId(room.getId())
                            .roomName(room.getRoomName())
                            .hostName(room.getHost().getNickname())
                            .numOfMembers(room.getMembers().size())
                            .createdAt(room.getCreatedAt())
                            .thumbnail(thumbnailUrl)
                            .build();
                })
                .collect(Collectors.toList());

        RoomListResponse roomListResponse = new RoomListResponse(roomListInfos);

        return DataResponseDto.of(roomListResponse, Code.OK.getMessage());
    }

    //특정 방 입장
    public ApiResponse getRoomDetails(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        boolean isMember = room.getMembers().stream().anyMatch(member -> member.getId().equals(userId));

        String thumbnailUrl = null;
        Optional<Thumbnail> thumbnail = thumbnailRepository.findByRoom(room);
        if (thumbnail.isPresent()) {
            thumbnailUrl = thumbnail.get().getThumbnailUrl();
        }

        //새로 들어온 방 참여 요청 수 계산
        Long numOfNewJoinRequests = joinRequestRepository.countByRoom(room);

        if (!isMember) {
            RoomDetailResponse roomDetailResponse = RoomDetailResponse.builder()
                    .isMember(false)
                    .roomId(room.getId())
                    .roomName(room.getRoomName())
                    .roomDescription(room.getRoomDescription())
                    .hostName(room.getHost().getNickname())
                    .thumbnail(thumbnailUrl).build();

            return DataResponseDto.of(roomDetailResponse,"해당 회원은 현재 이 Room의 Member로 등록되어 있지 않습니다");
        }

        List<String> members = room.getMembers().stream()
                .map(User::getNickname)
                .collect(Collectors.toList());

        List<MomentEnterInfo> moments = room.getMoments().stream()
                .map(moment -> MomentEnterInfo.builder()
                        .momentId(moment.getId())
                        .imageUrl(moment.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        RoomEnterInfo roomEnterInfo = new RoomEnterInfo(members, moments, numOfNewJoinRequests);

        RoomDetailResponse roomDetailResponse = RoomDetailResponse.builder()
                .isMember(true)
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .roomDescription(room.getRoomDescription())
                .hostName(room.getHost().getNickname())
                .roomDetail(roomEnterInfo)
                .thumbnail(thumbnailUrl)
                .build();

        return DataResponseDto.of(roomDetailResponse, Code.OK.getMessage());
    }
}
