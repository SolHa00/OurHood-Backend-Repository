package hello.photo.global.response;

import lombok.Getter;

@Getter
public class ErrorResponse extends ApiResponse {

    private Code code;

    private ErrorResponse(String message) {
        super(message);
    }

    private ErrorResponse(Code errorCode,  String message) {
        super(message);
    }

    private ErrorResponse(Code errorCode){
        super(errorCode.getCode());
    }

    public static ErrorResponse error(Code errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }

    public static ErrorResponse error(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse error(Code errorCode) {
        return new ErrorResponse(errorCode);
    }
}
