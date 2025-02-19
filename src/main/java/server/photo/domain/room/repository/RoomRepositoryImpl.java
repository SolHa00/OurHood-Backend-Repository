package server.photo.domain.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import server.photo.domain.room.entity.QRoom;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.entity.QUser;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> searchRooms(String query, String condition, Sort sort) {
        QRoom room = QRoom.room;
        QUser user = QUser.user;


        return List.of();
    }
}
