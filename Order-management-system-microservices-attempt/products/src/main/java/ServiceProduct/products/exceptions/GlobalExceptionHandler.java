package ServiceProduct.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> illegalAlrgument(MethodArgumentNotValidException me)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me.getMessage());
    }

    @ExceptionHandler(QuantityLimitExceededException.class)
    public ResponseEntity<String> limitExceeded(QuantityLimitExceededException qe)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(qe.getMessage());
    }


}
