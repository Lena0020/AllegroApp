package pl.kurs.java.AllegroAppl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentError.class)
    public ResponseEntity<ApiError> paymentErrorHandler(PaymentError e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ApiError> UserNotFoundHandler(UserNotFound e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.CONFLICT);
    }
}
