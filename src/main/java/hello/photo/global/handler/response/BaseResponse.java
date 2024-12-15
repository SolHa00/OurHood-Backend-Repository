package hello.photo.global.handler.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> {

    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    private BaseResponse(BaseResponseStatus status){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.result = null;
    }

    private BaseResponse(BaseResponseStatus status, T result){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.result = result;
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
    }

    public static <T> BaseResponse<T> fail(BaseResponseStatus status) {
        return new BaseResponse<>(status);
    }

}
