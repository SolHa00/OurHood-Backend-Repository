package hello.photo.global.exception;

import hello.photo.global.response.Code;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends GeneralException{
    public EntityNotFoundException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
