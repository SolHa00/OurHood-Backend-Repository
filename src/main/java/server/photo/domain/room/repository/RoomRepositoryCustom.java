package server.photo.domain.room.repository;

import server.photo.domain.room.entity.Room;

import java.util.List;

public interface RoomRepositoryCustom {

    // 방 생성일 기준 최신순 정렬
    List<Room> findRoomsByCreatedAtDesc();

    // 방 생성일 기준 오래된순 정렬
    List<Room> findRoomsByCreatedAtAsc();

    // 방 제목 기준 최신순 정렬
    List<Room> findRoomsByRoomNameDesc(String roomName);

    // 방 제목 기준 오래된순 정렬
    List<Room> findRoomsByRoomNameAsc(String roomName);

    // 호스트 닉네임 기준 최신순 정렬
    List<Room> findRoomsByHostNicknameDesc(String hostNickname);

    // 호스트 닉네임 기준 오래된순 정렬
    List<Room> findRoomsByHostNicknameAsc(String hostNickname);
}
