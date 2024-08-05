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

    public static <T> DataResponse<T> onSuccess(T result, String message) {
        return new DataResponse<>(result, message);
    }

    public static <T> DataResponse<T> onFailure(T result, String message) {
        return new DataResponse<>(null, message);
    }
}