package hello.photo.domain.room.dto.response;

import hello.photo.domain.moment.dto.response.MomentEnterInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoomEnterInfo {
    private List<String> members;
    private List<MomentEnterInfo> moments;
}
