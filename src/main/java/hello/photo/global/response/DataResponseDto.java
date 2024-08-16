package hello.photo.global.response;

import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ApiResponse {

    private final T result;

    private DataResponseDto(T result) {
        super(Code.OK.getMessage());
        this.result = result;
    }

    private DataResponseDto(T result, String message) {
        super(message);
        this.result = result;
    }

    public static <T> DataResponseDto<T> of(T result) {
        return new DataResponseDto<>(result);
    }

    public static <T> DataResponseDto<T> of(T result, String message) {
        return new DataResponseDto<>(result, message);
    }
}