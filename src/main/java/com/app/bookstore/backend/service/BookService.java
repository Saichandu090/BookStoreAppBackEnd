package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;

import java.util.Map;

public interface BookService
{
    public JsonResponseDTO addBook(BookRequestDTO requestDTO);

    public JsonResponseDTO getAllBooks();

    public JsonResponseDTO findById(Long bookId);

    public JsonResponseDTO findByName(String bookName);

    public JsonResponseDTO updateBook(Long bookId,BookRequestDTO requestDTO);

    public JsonResponseDTO deleteBook(Long bookId);

    public JsonResponseDTO updateBookQuantity(Long bookId, int quantity);

    public JsonResponseDTO updateBookPrice(Long bookId,double bookPrice);
}
