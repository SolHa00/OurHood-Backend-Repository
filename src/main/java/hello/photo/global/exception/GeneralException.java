package hello.photo.global.exception;

import hello.photo.global.response.Code;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{

    private final Code errorCode;

    public GeneralException() {
        super(Code.INTERNAL_SERVER_ERROR.getMessage());
        this.errorCode = Code.INTERNAL_SERVER_ERROR;
    }

    public GeneralException(String message) {
        super(Code.INTERNAL_SERVER_ERROR.getMessage());
        this.errorCode = Code.INTERNAL_SERVER_ERROR;
    }

    public GeneralException(String message, Throwable cause) {
        super(Code.INTERNAL_SERVER_ERROR.getMessage(message), cause);
        this.errorCode = Code.INTERNAL_SERVER_ERROR;
    }

    public GeneralException(Throwable cause) {
        super(Code.INTERNAL_SERVER_ERROR.getMessage(cause));
        this.errorCode = Code.INTERNAL_SERVER_ERROR;
    }

    public GeneralException(Code errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(Code errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralException(Code errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public GeneralException(Code errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}