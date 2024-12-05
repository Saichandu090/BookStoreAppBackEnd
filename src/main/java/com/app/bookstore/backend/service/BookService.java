package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface BookService
{
    public JsonResponseDTO addBook(String email, BookRequestDTO requestDTO);

    public JsonResponseDTO getBooks(String email);

    public JsonResponseDTO findById(String email,Long bookId);

    public JsonResponseDTO findByName(String email,String bookName);

    public JsonResponseDTO updateBook(String email,Long bookId,BookRequestDTO requestDTO);

    public JsonResponseDTO deleteBook(String email,Long bookId);
}
