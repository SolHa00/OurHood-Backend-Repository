package hello.photo.global.exception;

import hello.photo.global.response.Code;
import lombok.Getter;

@Getter
public class LogInFailException extends GeneralException {
    public LogInFailException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
