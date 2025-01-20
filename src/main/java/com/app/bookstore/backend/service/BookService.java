package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.BookRequestDTO;
import com.app.bookstore.backend.dto.JsonResponseDTO;

public interface BookService
{
    public JsonResponseDTO addBook(BookRequestDTO requestDTO);

    public JsonResponseDTO getAllBooks();

    public JsonResponseDTO findById(Long bookId);

    public JsonResponseDTO findByName(String bookName);

    public JsonResponseDTO updateBook(Long bookId,BookRequestDTO requestDTO);

    public JsonResponseDTO sortByPriceASC();

    public JsonResponseDTO sortByBookNameASC();

    public JsonResponseDTO deleteBook(Long bookId);

    public JsonResponseDTO updateBookQuantity(Long bookId, int quantity);

    public JsonResponseDTO updateBookPrice(Long bookId,double bookPrice);
}
