package hello.photo.domain.room.service;

import hello.photo.domain.room.dto.response.MomentEnterInfo;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    //방 생성
    public DataResponseDto<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다"));

        Room room = new Room();
        room.setRoomName(request.getRoomName());
        room.setRoomDescription(request.getRoomDescription());
        room.setHost(user);
        room.getMembers().add(user);
        room = roomRepository.save(room);

        MultipartFile thumbnail = request.getThumbnail();
        String imageUrl = s3FileService.uploadFile(thumbnail);

        Thumbnail thumbnailImage = new Thumbnail();
        thumbnailImage.setThumbnailUrl(imageUrl);
        thumbnailImage.setUser(user);
        thumbnailImage.setRoom(room);
        thumbnailRepository.save(thumbnailImage);

        room.setThumbnail(thumbnailImage);

        RoomCreateResponse roomResponse = new RoomCreateResponse(room.getId(), imageUrl);

        return DataResponseDto.of(roomResponse, Code.OK.getMessage());
    }

    //방 리스트 조회
    public DataResponseDto<RoomListResponse> getRooms(String order, int roomsPerPage, int page, String condition, String q) {
        Pageable pageable;
        if (order.equals("date_desc")) {
            pageable = PageRequest.of(page - 1, roomsPerPage, Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if (order.equals("date_asc")) {
            pageable = PageRequest.of(page - 1, roomsPerPage, Sort.by(Sort.Direction.ASC, "createdAt"));
        } else {
            pageable = PageRequest.of(page - 1, roomsPerPage);
        }

        Page<Room> roomsPage;
        if (q != null && !q.isEmpty()) {
            if ("room".equals(condition)) {
                roomsPage = roomRepository.findByRoomNameContaining(q, pageable);
            } else if ("host".equals(condition)) {
                roomsPage = roomRepository.findByHostNicknameContaining(q, pageable);
            } else {
                roomsPage = roomRepository.findAll(pageable);
            }
        } else {
            roomsPage = roomRepository.findAll(pageable);
        }

        List<RoomListInfo> rooms = roomsPage.getContent().stream()
                .map(room -> {
                    // 썸네일 URL 가져오기
                    String thumbnailUrl = null;
                    Optional<Thumbnail> thumbnail = thumbnailRepository.findByRoom(room);
                    if (thumbnail.isPresent()) {
                        thumbnailUrl = thumbnail.get().getThumbnailUrl();
                    }

                    return new RoomListInfo(
                            room.getId(),
                            room.getRoomName(),
                            room.getHost().getNickname(),
                            room.getMembers().size(),
                            room.getCreatedAt(),
                            thumbnailUrl  // 썸네일 URL 추가
                    );
                })
                .collect(Collectors.toList());
        Long totalPages = (long) Math.ceil((double) roomsPage.getTotalElements() / roomsPerPage);

        RoomListResponse roomListResponse = new RoomListResponse(totalPages, rooms);

        return DataResponseDto.of(roomListResponse, Code.OK.getMessage());
    }

    //특정 방 입장
    public ApiResponse getRoomDetails(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 Room을 찾을 수 없습니다"));

        boolean isMember = room.getMembers().stream().anyMatch(member -> member.getId().equals(userId));

        String thumbnailUrl = null;
        Optional<Thumbnail> thumbnail = thumbnailRepository.findByRoom(room);
        if (thumbnail.isPresent()) {
            thumbnailUrl = thumbnail.get().getThumbnailUrl();
        }

        if (!isMember) {
            RoomDetailResponse roomDetailResponse = new RoomDetailResponse(
                    isMember,
                    room.getId(),
                    room.getRoomName(),
                    room.getRoomDescription(),
                    room.getHost().getNickname(),
                    null,
                    thumbnailUrl
            );
            return DataResponseDto.of(roomDetailResponse,"해당 회원은 현재 이 Room의 Member로 등록되어 있지 않습니다");
        }

        List<String> members = room.getMembers().stream()
                .map(User::getNickname)
                .collect(Collectors.toList());

        List<MomentEnterInfo> moments = room.getMoments().stream()
                .map(moment -> new MomentEnterInfo(moment.getId(), moment.getImageUrl()))
                .collect(Collectors.toList());

        RoomEnterInfo roomEnterInfo = new RoomEnterInfo(members, moments);

        RoomDetailResponse roomDetailResponse = new RoomDetailResponse(
                isMember,
                room.getId(),
                room.getRoomName(),
                room.getRoomDescription(),
                room.getHost().getNickname(),
                roomEnterInfo,
                thumbnailUrl
        );

        return DataResponseDto.of(roomDetailResponse, Code.OK.getMessage());
    }
}
