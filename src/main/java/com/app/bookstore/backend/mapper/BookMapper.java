package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookMapper
{
    @Autowired
    private BookRepository bookRepository;


    public JsonResponseDTO noAuthority()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage("No Authority");
        responseDTO.setData(null);
        return responseDTO;
    }

    // Converting RequestDTO to Book
    public Book addBook(BookRequestDTO requestDTO)
    {
        Book book=new Book();
        book.setBookId(requestDTO.getId());
        book.setBookName(requestDTO.getName());
        book.setAuthor(requestDTO.getAuthor());
        book.setQuantity(requestDTO.getQuantity());
        book.setPrice(requestDTO.getPrice());
        book.setDescription(requestDTO.getDescription());
        return book;
    }

    // Returning message after saving Book in DB
    public JsonResponseDTO bookSaved(Book book)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Book Saved SuccessFully");
        responseDTO.setData(List.of(book));
        return responseDTO;
    }
}
