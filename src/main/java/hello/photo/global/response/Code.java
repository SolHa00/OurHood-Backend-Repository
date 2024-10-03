package hello.photo.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum Code {
    //성공
    OK(HttpStatus.OK, "200", "OK"),
    //에러
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "Bad request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증 실패"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없음"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", "Not Found"),
    DUPLICATED(HttpStatus.CONFLICT, "409", "Conflict"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러"),
    //커스텀 에러
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "1201", "해당 이메일이 이미 존재합니다."),
    USER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "1202", "해당 닉네임이 이미 존재합니다."),
    JOIN_REQUEST_DUPLICATED(HttpStatus.CONFLICT, "5201", "이미 해당 방에 참여 요청을 보냈습니다."),
    INVITATION_REQUEST_DUPLICATED(HttpStatus.CONFLICT, "6201", "이미 이 방에 초대된 사용자입니다."),
    ALREADY_INVITED_USER(HttpStatus.CONFLICT, "6202", "이미 이 방의 멤버입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "6101", "해당 닉네임이 존재하지 않습니다."),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "1301", "로그인 실패"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "0301", "AccessToken이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "0302", "RefreshToken이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable throwable) {
        return this.getMessage(this.getMessage(this.getMessage() + " - " + throwable.getMessage()));
    }

    //전달받은 예외의 message가 존재할 경우 해당 message를 반환, 없을 경우 필드 값 message를 반환
    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
