package hello.photo.global.exception;

import hello.photo.global.response.Code;
import lombok.Getter;

@Getter
public class DuplicateRequestException extends GeneralException{
    public DuplicateRequestException(String message) {
        super(Code.DUPLICATED, message);
    }
}
