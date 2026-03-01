package com.order_management.Order_management_system.exception;

public class QuantityLimitExceededException extends RuntimeException {
    public QuantityLimitExceededException(String message)
    {
        super(message);
    }
}
