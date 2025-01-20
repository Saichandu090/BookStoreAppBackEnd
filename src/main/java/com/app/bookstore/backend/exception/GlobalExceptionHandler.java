package com.app.bookstore.backend.exception;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.mapper.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private final ExceptionMapper exceptionMapper=new ExceptionMapper();

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<JsonResponseDTO> userNotFound(UserNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<JsonResponseDTO> bookNotFound(BookNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<JsonResponseDTO> invalidToken(InvalidTokenException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<JsonResponseDTO> cartNotFound(CartNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<JsonResponseDTO> insufficientFound(InsufficientStockException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<JsonResponseDTO> orderNotFound(OrderNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<JsonResponseDTO> runTimeFound(RuntimeException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<JsonResponseDTO> addressNotFound(AddressNotFoundException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<JsonResponseDTO> badCredentials(BadCredentialsException e)
    {
        return new ResponseEntity<>(exceptionMapper.exception(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponseDTO> exception(Exception exception)
    {
        return new ResponseEntity<>(exceptionMapper.exception(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }

}
