package hello.photo.global.exception;

import hello.photo.global.response.Code;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{

    private final Code errorCode;

    public GeneralException(Code errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
