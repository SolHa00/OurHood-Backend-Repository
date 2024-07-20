package hello.photo.controller;

import hello.photo.dto.room.request.RoomDetailRequest;
import hello.photo.dto.room.request.RoomRequest;
import hello.photo.dto.room.response.RoomDetailResponse;
import hello.photo.dto.room.response.RoomResponse;
import hello.photo.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "Room API", description = "Room 관련 API")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "방 생성")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        RoomResponse response = roomService.createRoom(request);
        return ResponseEntity.ok(response);
    }

//    @GetMapping
//    @Operation(summary = "방 리스트 조회")
//    public ResponseEntity<RoomListResponse> roomList(@RequestParam String order, @RequestParam int roomsPerPage, @RequestParam int page) {
//        RoomListResponse response = roomService.getRooms(order, roomsPerPage, page);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/{roomId}")
    @Operation(summary = "특정 방 조회", description = "방 멤버에 속하면 입장, 속하지 않는다면 참여 요청을 누르는 페이지로..")
    public ResponseEntity<RoomDetailResponse> getRoom(@PathVariable Long roomId, @RequestBody RoomDetailRequest request) {
        RoomDetailResponse response = roomService.getRoomDetails(roomId, request.getUserId());
        return ResponseEntity.ok(response);
    }

}