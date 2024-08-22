package hello.photo.global.response;

import lombok.Getter;

@Getter
public class ErrorResponseDto extends ApiResponse {

    private final ErrorDetail errorDetail;

    private ErrorResponseDto(Code errorCode, String message, String type) {
        super(errorCode.getMessage(message));
        this.errorDetail = ErrorDetail.of(type);
    }

    private ErrorResponseDto(String message, ErrorDetail errorDetail) {
        super(message);
        this.errorDetail = errorDetail;
    }

    private ErrorResponseDto(Code errorCode, String type){
        super(errorCode.getMessage());
        this.errorDetail = ErrorDetail.of(type);
    }

    public static ErrorResponseDto of(Code errorCode, String type) {
        return new ErrorResponseDto(errorCode, type);
    }

    public static ErrorResponseDto of(String message, String type) {
        return new ErrorResponseDto(message, ErrorDetail.of(type));
    }
}
