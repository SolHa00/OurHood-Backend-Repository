package server.photo.domain.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.photo.domain.room.entity.Room;

import java.util.List;

import static server.photo.domain.room.entity.QRoom.room;
import static server.photo.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> findRoomsByCreatedAtDesc() {
        return queryFactory.selectFrom(room)
                .orderBy(room.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Room> findRoomsByCreatedAtAsc() {
        return queryFactory.selectFrom(room)
                .orderBy(room.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Room> findRoomsByRoomNameDesc(String roomName) {
        return queryFactory.selectFrom(room)
                .where(room.roomName.contains(roomName))
                .orderBy(room.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Room> findRoomsByRoomNameAsc(String roomName) {
        return queryFactory.selectFrom(room)
                .where(room.roomName.contains(roomName))
                .orderBy(room.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Room> findRoomsByHostNicknameDesc(String hostNickname) {
        return queryFactory.selectFrom(room)
                .join(user)
                .on(room.hostId.eq(user.id))
                .where(user.nickname.contains(hostNickname))
                .orderBy(room.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Room> findRoomsByHostNicknameAsc(String hostNickname) {
        return queryFactory.selectFrom(room)
                .join(user)
                .on(room.hostId.eq(user.id))
                .where(user.nickname.contains(hostNickname))
                .orderBy(room.createdAt.asc())
                .fetch();
    }
}
