package hello.photo.controller;

import hello.photo.dto.room.request.RoomRequest;
import hello.photo.dto.room.response.RoomResponse;
import hello.photo.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Room API", description = "Room 관련 API")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    @Operation(summary = "방 생성")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        RoomResponse response = roomService.createRoom(request);
        return ResponseEntity.ok(response);
    }

//    @GetMapping
//    @Operation(summary = "방 리스트 조회")
//    public ResponseEntity<RoomListResponse> getRooms(@RequestParam String order, @RequestParam int roomsPerPage, @RequestParam int page) {
//        RoomListResponse response = roomService.getRooms(order, roomsPerPage, page);
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/{roomId}")
//    @Operation(summary = "특정 방 조회")
//    public ResponseEntity<RoomDetailResponse> getRoom(@PathVariable Long roomId, @RequestParam Long userId) {
//        RoomDetailResponse response = roomService.getRoom(roomId, userId);
//        return ResponseEntity.ok(response);
//    }

}
