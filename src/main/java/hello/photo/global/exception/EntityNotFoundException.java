package hello.photo.global.exception;

import hello.photo.global.response.Code;

public class EntityNotFoundException extends GeneralException{
    public EntityNotFoundException(String message) {
        super(Code.NOT_FOUND, message);
    }
}
