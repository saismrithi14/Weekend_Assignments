package com.order_management.Order_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)

    public ResponseEntity<String> handleMemberNotFound(MemberNotFoundException ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidUUID(MethodArgumentTypeMismatchException me)
    {
        return new ResponseEntity<>("Invalid UUID", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> illegalArgument(MethodArgumentNotValidException me)
    {
        return new ResponseEntity<>("Invalid input has been given", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)

    public ResponseEntity<String> productNotFound(ProductNotFoundException pe)
    {
        return new ResponseEntity<>(pe.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(QuantityLimitExceededException.class)

    public ResponseEntity<String> limitExceeded(QuantityLimitExceededException qe)
    {
        return new ResponseEntity<>(qe.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> orderNotPresent(OrderNotFoundException oe)
    {
        return new ResponseEntity<>(oe.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)

    public ResponseEntity<String> incompatible(HttpMessageNotReadableException he)
    {
        return new ResponseEntity<>("Incompatible types between input and object", HttpStatus.BAD_REQUEST);
    }




}
