package server.photo.global.handler.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseResponseStatus {

    /**
     * 200 : 요청 성공
     */
    SUCCESS(200, HttpStatus.OK, "요청 성공"),

    /**
     * 4XX Error
     */
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "인증 실패"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "접근 권한이 없음"),
    NOT_FOUND( 404, HttpStatus.NOT_FOUND,"Not Found"),
    DUPLICATED( 409, HttpStatus.CONFLICT, "Conflict"),

    /**
     * Custom Error
     */
    USER_EMAIL_DUPLICATED(1201, HttpStatus.CONFLICT, "해당 이메일이 이미 존재"),
    USER_NICKNAME_DUPLICATED(1202, HttpStatus.CONFLICT, "해당 닉네임이 이미 존재"),
    JOIN_REQUEST_DUPLICATED(5201, HttpStatus.CONFLICT, "이미 해당 방에 참여 요청을 보냄."),
    INVITATION_REQUEST_DUPLICATED(6201, HttpStatus.CONFLICT, "이미 이 방에 초대된 사용자"),
    ALREADY_INVITED_USER(6202, HttpStatus.CONFLICT,"이미 이 방의 멤버."),
    MEMBER_NOT_FOUND(6101, HttpStatus.NOT_FOUND,"해당 닉네임이 존재하지 않음"),
    LOGIN_FAIL(1301, HttpStatus.UNAUTHORIZED, "로그인 실패"),
    ACCESS_TOKEN_EXPIRED(0301, HttpStatus.UNAUTHORIZED, "AccessToken 만료"),
    REFRESH_TOKEN_EXPIRED(0302, HttpStatus.UNAUTHORIZED, "RefreshToken 만료"),

    /**
     * 5XX Error
     */
    INTERNAL_SERVER_ERROR( 500, HttpStatus.INTERNAL_SERVER_ERROR,"서버 에러");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

}
