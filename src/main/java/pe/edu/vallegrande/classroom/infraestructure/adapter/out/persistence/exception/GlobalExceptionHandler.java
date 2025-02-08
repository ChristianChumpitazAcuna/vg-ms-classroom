package pe.edu.vallegrande.classroom.infraestructure.adapter.out.persistence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiError>> handleValidationException(WebExchangeBindException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            apiError.addError(error.getField(), error.getDefaultMessage());
        });

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ApiError>> handleClassroomNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.addError("message", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError));
    }

    @ExceptionHandler(CapacityExceededException.class)
    public Mono<ResponseEntity<ApiError>> handleCapacityExceededException(CapacityExceededException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.addError("message", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError));
    }
}
