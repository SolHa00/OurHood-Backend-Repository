package hello.photo.global.response;

import lombok.Getter;

@Getter
public class DataResponse<T> extends ApiResponse {

    private final T result;

    private DataResponse(T result) {
        super(Code.OK.getMessage());
        this.result = result;
    }

    private DataResponse(T result, String message) {
        super(message);
        this.result = result;
    }

    public static <T> DataResponse<T> of(T result) {
        return new DataResponse<>(result);
    }

    public static <T> DataResponse<T> of(T result, String message) {
        return new DataResponse<>(result, message);
    }

}