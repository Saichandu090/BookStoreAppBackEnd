package com.app.bookstore.backend.exception;

public class BookNotFoundException extends RuntimeException
{
    public BookNotFoundException(String message)
    {
        super(message);
    }
}
