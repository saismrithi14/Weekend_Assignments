package ServiceOrder.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    ResponseEntity<String> memberNotFound(MemberNotFoundException me)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me.getMessage());
    }
    @ExceptionHandler(QuantityLimitExceededException.class)
    public ResponseEntity<String> exceededQuantity(QuantityLimitExceededException qe)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(qe.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotThere(ProductNotFoundException pe)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pe.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> orderNotThere(OrderNotFoundException oe)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oe.getMessage());
    }
}
