package hello.photo.domain.room.service;

import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.room.dto.request.RoomRequest;
import hello.photo.domain.room.dto.response.RoomDetailResponse;
import hello.photo.domain.room.dto.response.RoomResponse;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    //방 리스트 조회
//    public RoomListResponse getRooms(String order, int roomsPerPage, int page) {
//        RoomListResponse response
//    }
}