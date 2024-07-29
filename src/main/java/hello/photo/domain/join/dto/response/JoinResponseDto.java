package hello.photo.domain.join.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JoinResponseDto {
    private List<JoinRequesDetail> joinList;

    public static class JoinRequesDetail {
        private Long joinId;
        private String nickName;

        public JoinRequesDetail(Long joinId, String nickName) {
            this.joinId = joinId;
            this.nickName = nickName;
        }

        public Long getJoinId() {
            return joinId;
        }

        public void setJoinId(Long joinId) {
            this.joinId = joinId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
