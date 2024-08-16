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
    DUPLICATED(HttpStatus.CONFLICT, "409", "중복"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러");


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