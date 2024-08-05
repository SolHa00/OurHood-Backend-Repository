package hello.photo.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }

    public static ApiResponse onSuccess(Code successCode) {
        return new ApiResponse(successCode.getMessage());
    }

    public static ApiResponse onFailure(Code errorCode) {
        return new ApiResponse(errorCode.getMessage());
    }

    public static ApiResponse onFailure(String message) {
        return new ApiResponse(message);
    }
}