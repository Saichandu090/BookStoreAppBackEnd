package com.app.bookstore.backend.exception;

public class CartNotFoundException extends RuntimeException
{
    public CartNotFoundException(String message)
    {
        super(message);
    }
}
