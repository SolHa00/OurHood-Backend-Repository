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

}