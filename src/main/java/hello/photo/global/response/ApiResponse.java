package hello.photo.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse {

    private final String message;

    public static ApiResponse of(String message) {
        return new ApiResponse(message);
    }

    public static ApiResponse of(String message, Code errorCode, Exception e) {
        return new ApiResponse(errorCode.getMessage(e));
    }

    public static ApiResponse of(Code errorCode, String message) {
        return new ApiResponse(errorCode.getMessage(message));
    }
}
