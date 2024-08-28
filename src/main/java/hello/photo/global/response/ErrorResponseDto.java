package hello.photo.global.response;

import lombok.Getter;

@Getter
public class ErrorResponseDto extends ApiResponse {

    private final String code;

    private ErrorResponseDto(String message, String code) {
        super(message);
        this.code = code;
    }

    private ErrorResponseDto(Code errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public static ErrorResponseDto of(String message, String code) {
        return new ErrorResponseDto(message, code);
    }

    public static ErrorResponseDto of(Code errorCode, String message) {
        return new ErrorResponseDto(errorCode, message);
    }
}
