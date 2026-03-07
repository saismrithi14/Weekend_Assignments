package com.order_management.Order_management_system.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message)
    {
        super(message);
    }
}
