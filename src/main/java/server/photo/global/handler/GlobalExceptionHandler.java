package server.photo.global.handler;

import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleGeneralException(BaseException e) {
        return ResponseEntity.status(e.getStatus().getHttpStatus()).body(BaseResponse.fail(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR));
    }
}
