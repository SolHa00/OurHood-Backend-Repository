package hello.photo.service;

import hello.photo.domain.Room;
import hello.photo.domain.User;
import hello.photo.dto.room.request.RoomRequest;
import hello.photo.dto.room.response.RoomResponse;
import hello.photo.repository.RoomRepository;
import hello.photo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomResponse createRoom(RoomRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저 없음."));
        Room room = new Room();
        room.setRoomName(request.getRoomName());
        room.setRoomDescription(request.getRoomDescription());
        room.setHost(user);
        room = roomRepository.save(room);

        return new RoomResponse(room.getId());
    }

//    public RoomListResponse getRooms(String order, int roomsPerPage, int page) {
//    }


}
