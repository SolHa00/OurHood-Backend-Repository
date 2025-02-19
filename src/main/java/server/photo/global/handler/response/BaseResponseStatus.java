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
     * 400 BAD_REQUEST 잘못된 요청
     */
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청"),
    BAD_REQUEST_ORDER(40001, HttpStatus.BAD_REQUEST, "파라미터 바인딩 실패(order)"),
    BAD_REQUEST_CONDITION(40002, HttpStatus.BAD_REQUEST, "파라미터 바인딩 실패(condition)"),

    /**
     * 401 UNAUTHORIZED 권한없음(인증 실패)
     */
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "인증 실패"),
    LOGIN_FAIL(40101, HttpStatus.UNAUTHORIZED, "로그인 실패"),
    ACCESS_TOKEN_EXPIRED(40102, HttpStatus.UNAUTHORIZED, "AccessToken 만료"),
    REFRESH_TOKEN_EXPIRED(40103, HttpStatus.UNAUTHORIZED, "RefreshToken 만료"),

    /**
     * 403 FORBIDDEN 권한없음
     */
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "접근 권한이 없음"),

    /**
     * 404 NOT_FOUND 잘못된 리소스 접근
     */
    NOT_FOUND( 404, HttpStatus.NOT_FOUND,"Not Found"),
    USER_NOT_FOUND(40401, HttpStatus.NOT_FOUND,"해당 User를 찾을 수 않음"),
    ROOM_NOT_FOUND(40402, HttpStatus.NOT_FOUND, "해당 Room을 찾을 수 없음"),
    MOMENT_NOT_FOUND(40403, HttpStatus.NOT_FOUND, "해당 Moment를 찾을 수 없음"),
    JOIN_REQUEST_NOT_FOUND(40404, HttpStatus.NOT_FOUND, "해당 Join Request를 찾을 수 없음"),
    INVITATION_NOT_FOUND(40405, HttpStatus.NOT_FOUND, "해당 Invitation을 찾을 수 없음"),
    COMMENT_NOT_FOUND(40406, HttpStatus.NOT_FOUND, "해당 Comment를 찾을 수 없음"),

    /**
     * 409 CONFLICT 중복된 리소스
     */
    DUPLICATED( 409, HttpStatus.CONFLICT, "Conflict"),
    USER_EMAIL_DUPLICATED(40901, HttpStatus.CONFLICT, "해당 이메일이 이미 존재"),
    USER_NICKNAME_DUPLICATED(40902, HttpStatus.CONFLICT, "해당 닉네임이 이미 존재"),
    JOIN_REQUEST_DUPLICATED(40903, HttpStatus.CONFLICT, "이미 해당 방에 참여 요청을 보냄."),
    INVITATION_REQUEST_DUPLICATED(40904, HttpStatus.CONFLICT, "이미 이 방에 초대된 사용자"),
    ALREADY_INVITED_USER(40905, HttpStatus.CONFLICT,"이미 이 방의 멤버."),

    /**
     * 5XX Error
     */
    INTERNAL_SERVER_ERROR( 500, HttpStatus.INTERNAL_SERVER_ERROR,"서버 에러");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

}
