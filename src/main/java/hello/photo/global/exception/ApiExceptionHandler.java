package hello.photo.global.exception;

import hello.photo.domain.user.exception.UserNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.of(Code.UNAUTHORIZED, e.getClass().getSimpleName()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> general(GeneralException e) {
        Code errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus();

        return ResponseEntity.status(status)
                .body(ErrorResponseDto.of(
                        errorCode,
                        e.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> general(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.of("RuntimeException 발생"
                ));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> exception(Exception e) {
        return ResponseEntity.status(500)
                .body(ApiResponse.of("Exception 발생"));
    }

}
