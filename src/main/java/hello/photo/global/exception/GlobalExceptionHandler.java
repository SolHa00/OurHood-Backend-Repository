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
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.of(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handlerEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.of(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDto.of(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleGeneralException(GeneralException e) {
        Code errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus();

        return ResponseEntity.status(status)
                .body(ErrorResponseDto.of(
                        e.getMessage(),
                        e.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.of("RuntimeException 발생"));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.of("Exception 발생"));
    }
}
