package com.app.bookstore.backend.exception;

import com.app.bookstore.backend.mapper.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private final ExceptionMapper exceptionMapper=new ExceptionMapper();

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> bookNotFound(BookNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> invalidToken(InvalidTokenException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> cartNotFound(CartNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<?> insufficientFound(InsufficientStockException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> insufficientFound(OrderNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> insufficientFound(RuntimeException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> insufficientFound(AddressNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> insufficientFound(BadCredentialsException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }


}
