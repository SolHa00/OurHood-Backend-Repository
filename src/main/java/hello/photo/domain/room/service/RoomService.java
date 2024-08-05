package hello.photo.domain.room.service;

import hello.photo.domain.room.dto.Rooms;
import hello.photo.domain.room.dto.request.RoomRequest;
import hello.photo.domain.room.dto.response.RoomDetailResponse;
import hello.photo.domain.room.dto.response.RoomListResponse;
import hello.photo.domain.room.dto.response.RoomResponse;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomResponse createRoom(RoomRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저 존재하지 않음."));
        Room room = new Room();
        room.setRoomName(request.getRoomName());
        room.setRoomDescription(request.getRoomDescription());
        room.setHost(user);
        room.getMembers().add(user);
        room = roomRepository.save(room);

        return new RoomResponse(room.getId());
    }

    public RoomDetailResponse getRoomDetails(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 Room 존재하지 않음"));

        boolean isMember = room.getMembers().stream().anyMatch(member -> member.getId().equals(userId));

        // 해당 유저가 Room Member에 속한다면
        if (isMember) {

        }

        return new RoomDetailResponse(isMember, room.getId(), room.getRoomName(), room.getRoomDescription(), room.getHost().getNickname());
    }

    public DataResponse<RoomListResponse> getRooms(String order, int roomsPerPage, int page, String condition, String query) {
        Pageable pageable;
        if (order.equals("date_desc")) {
            pageable = PageRequest.of(page - 1, roomsPerPage, Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if (order.equals("date_asc")) {
            pageable = PageRequest.of(page - 1, roomsPerPage, Sort.by(Sort.Direction.ASC, "createdAt"));
        } else {
            pageable = PageRequest.of(page - 1, roomsPerPage);
        }

        Page<Room> roomsPage;
        if (query != null && !query.isEmpty()) {
            if ("room".equals(condition)) {
                roomsPage = roomRepository.findByRoomNameContaining(query, pageable);
            } else if ("host".equals(condition)) {
                roomsPage = roomRepository.findByHostNicknameContaining(query, pageable);
            } else {
                roomsPage = roomRepository.findAll(pageable);
            }
        } else {
            roomsPage = roomRepository.findAll(pageable);
        }

        List<Rooms> rooms = roomsPage.getContent().stream()
                .map(room -> new Rooms(
                        room.getId(),
                        room.getRoomName(),
                        room.getHost().getNickname(),
                        room.getMembers().size(),
                        room.getCreatedAt()))
                .collect(Collectors.toList());

        RoomListResponse roomListResponse = new RoomListResponse(
                rooms
        );

        return DataResponse.onSuccess(roomListResponse, Code.OK.getMessage());
    }

}