package hello.photo.domain.room.service;

import hello.photo.domain.moment.dto.response.MomentEnterInfo;
import hello.photo.domain.room.dto.response.*;
import hello.photo.domain.room.dto.request.RoomCreateRequest;
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

    //방 생성
    public DataResponse<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저 존재하지 않음."));

        Room room = new Room();
        room.setRoomName(request.getRoomName());
        room.setRoomDescription(request.getRoomDescription());
        room.setHost(user);
        room.getMembers().add(user);
        room = roomRepository.save(room);

        RoomCreateResponse roomResponse = new RoomCreateResponse(room.getId());

        return DataResponse.of(roomResponse, Code.OK.getMessage());
    }

    //특정 방 입장
    public DataResponse<RoomDetailResponse> getRoomDetails(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 Room 존재하지 않음"));

        boolean isMember = room.getMembers().stream().anyMatch(member -> member.getId().equals(userId));

        List<String> members = room.getMembers().stream()
                .map(User::getNickname)
                .collect(Collectors.toList());

        List<MomentEnterInfo> moments = room.getMoments().stream()
                .map(moment -> new MomentEnterInfo(moment.getId(), moment.getImageUrl()))
                .collect(Collectors.toList());

        RoomEnterInfo roomEnterInfo = new RoomEnterInfo(members, moments);

        RoomDetailResponse roomDetailResponse = new RoomDetailResponse(isMember, room.getId(), room.getRoomName(), room.getRoomDescription(), room.getHost().getNickname(), roomEnterInfo);

        // 해당 유저가 Room Member에 등록되어 있다면?
        if (isMember) {
            return DataResponse.of(roomDetailResponse, Code.OK.getMessage());
        }

        return DataResponse.of(null, "해당 회원은 현재 이 Room의 Member로 등록되어 있지 않습니다.");
    }

    //방 리스트 조회
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

        List<RoomsMyPageInfo> rooms = roomsPage.getContent().stream()
                .map(room -> new RoomsMyPageInfo(
                        room.getId(),
                        room.getRoomName(),
                        room.getHost().getNickname(),
                        room.getMembers().size(),
                        room.getCreatedAt()))
                .collect(Collectors.toList());

        RoomListResponse roomListResponse = new RoomListResponse(rooms);

        return DataResponse.of(roomListResponse, Code.OK.getMessage());
    }

}