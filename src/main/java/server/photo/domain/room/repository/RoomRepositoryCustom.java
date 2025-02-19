package server.photo.domain.room.repository;

import org.springframework.data.domain.Sort;
import server.photo.domain.room.entity.Room;

import java.util.List;

public interface RoomRepositoryCustom {
    List<Room> searchRooms(String query, String condition, Sort sort);
}
