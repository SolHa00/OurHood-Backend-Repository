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
    INVITATION_ALREADY_SENT(20001, HttpStatus.OK, "이미 해당 방에서 초대 요청이 발송됨"),
    JOIN_REQUEST_ALREADY_SENT(20002, HttpStatus.OK, "해당 사용자는 이미 참여 요청을 보낸 상태"),

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
    NOT_FOUND_USER(40401, HttpStatus.NOT_FOUND,"해당 User를 찾을 수 않음"),
    NOT_FOUND_ROOM(40402, HttpStatus.NOT_FOUND, "해당 Room을 찾을 수 없음"),
    NOT_FOUND_MOMENT(40403, HttpStatus.NOT_FOUND, "해당 Moment를 찾을 수 없음"),
    NOT_FOUND_JOIN_REQUEST(40404, HttpStatus.NOT_FOUND, "해당 Join Request를 찾을 수 없음"),
    NOT_FOUND_INVITATION(40405, HttpStatus.NOT_FOUND, "해당 Invitation을 찾을 수 없음"),
    NOT_FOUND_COMMENT(40406, HttpStatus.NOT_FOUND, "해당 Comment를 찾을 수 없음"),

    /**
     * 409 CONFLICT 중복된 리소스
     */
    CONFLICT(409, HttpStatus.CONFLICT, "중복된 리소스"),
    DUPLICATE_EMAIL(40901, HttpStatus.CONFLICT, "해당 이메일이 이미 존재"),
    DUPLICATE_NICKNAME(40902, HttpStatus.CONFLICT, "해당 닉네임이 이미 존재"),
    DUPLICATE_JOIN_REQUEST(40903, HttpStatus.CONFLICT, "이미 해당 방에 참여 요청을 보냄."),
    DUPLICATE_INVITATION_REQUEST(40904, HttpStatus.CONFLICT, "이미 이 방에 초대된 사용자"),
    ALREADY_INVITED_USER(40905, HttpStatus.CONFLICT,"이미 이 방의 멤버."),

    /**
     * 5XX Error
     */
    INTERNAL_SERVER_ERROR( 500, HttpStatus.INTERNAL_SERVER_ERROR,"서버 에러");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

}
