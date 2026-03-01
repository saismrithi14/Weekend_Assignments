package com.order_management.Order_management_system.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message)
    {
        super(message);
    }
}
