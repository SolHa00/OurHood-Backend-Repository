package hello.photo.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {
    //성공
    OK(HttpStatus.OK, "200", "OK"),
    //에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "클라 에러"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증 실패"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없음"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"404", "해당 유저가 존재하지 않음");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}