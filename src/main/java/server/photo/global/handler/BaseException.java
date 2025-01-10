package server.photo.global.handler;

import server.photo.global.handler.response.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private final BaseResponseStatus status;
}
