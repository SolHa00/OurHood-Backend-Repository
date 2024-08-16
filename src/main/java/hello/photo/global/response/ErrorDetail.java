package hello.photo.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorDetail {
    private final String type;

    public static ErrorDetail of(String type) {
        return new ErrorDetail(type);
    }
}
