package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.dto.BookRequestDTO;
import com.app.bookstore.backend.dto.BookResponseDTO;
import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.model.Book;

import java.util.List;

public class BookMapper
{
    public JsonResponseDTO noAuthority()
    {
        return JsonResponseDTO.builder()
                .data(null)
                .result(false)
                .message("No Authority").build();
    }

    // Converting RequestDTO to Book
    public Book addBook(BookRequestDTO requestDTO)
    {
        return Book.builder()
                .bookName(requestDTO.getName())
                .bookLogo(requestDTO.getBookLogo())
                .author(requestDTO.getAuthor())
                .quantity(requestDTO.getQuantity())
                .price(requestDTO.getPrice())
                .description(requestDTO.getDescription())
                .cartBookQuantity(0).build();
    }

    public Book updateBook(Long bookId,BookRequestDTO requestDTO)
    {
        Book book=addBook(requestDTO);
        book.setBookId(bookId);
        return book;
    }

    public JsonResponseDTO deleteBook(String bookName)
    {
        return JsonResponseDTO.builder()
                .message("Book with name '"+bookName+"' has deleted Successfully")
                .data(null)
                .result(true).build();
    }

    // Returning message after saving Book in DB
    public JsonResponseDTO bookSaved(Book book)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .data(List.of(book))
                .message("Book Saved SuccessFully").build();
    }

    public JsonResponseDTO bookUpdated(Book book,String message)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .data(List.of(book))
                .message(message).build();
    }

    public JsonResponseDTO bookFound(Book book)
    {
        BookResponseDTO dto=convertBook(book);
        return JsonResponseDTO.builder()
                .result(true)
                .data(List.of(dto))
                .message("Book Retrieved SuccessFully").build();
    }

    public JsonResponseDTO bookNotSaved()
    {
        return JsonResponseDTO.builder()
                .result(false)
                .data(null)
                .message("Book not saved!!").build();
    }

    public BookResponseDTO convertBook(Book book)
    {
        return BookResponseDTO.builder()
                .id(book.getBookId())
                .name(book.getBookName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .bookLogo(book.getBookLogo()).build();
    }

    public JsonResponseDTO sendBookList(List<Book> books)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .message("Books retrieved Successfully")
                .data(books).build();
    }

}
