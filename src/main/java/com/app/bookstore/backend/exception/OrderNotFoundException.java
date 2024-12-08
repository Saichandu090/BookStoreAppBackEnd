package com.app.bookstore.backend.exception;

public class OrderNotFoundException extends RuntimeException
{
    public OrderNotFoundException(String message)
    {
        super(message);
    }
}
