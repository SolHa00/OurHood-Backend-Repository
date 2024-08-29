package hello.photo.global.exception;

import hello.photo.global.response.Code;
import lombok.Getter;

@Getter
public class DuplicateException extends GeneralException{
    public DuplicateException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
