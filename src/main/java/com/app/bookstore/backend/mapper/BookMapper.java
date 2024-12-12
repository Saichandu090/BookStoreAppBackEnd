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
        book.setBookLogo(requestDTO.getBookLogo());
        book.setBookName(requestDTO.getName());
        book.setAuthor(requestDTO.getAuthor());
        book.setQuantity(requestDTO.getQuantity());
        book.setPrice(requestDTO.getPrice());
        book.setDescription(requestDTO.getDescription());
        book.setCartBookQuantity(0);
        return book;
    }

    public Book updateBook(Long bookId,BookRequestDTO requestDTO)
    {
        Book book=new Book();
        book.setBookId(bookId);
        book.setBookLogo(requestDTO.getBookLogo());
        book.setBookName(requestDTO.getName());
        book.setAuthor(requestDTO.getAuthor());
        book.setQuantity(requestDTO.getQuantity());
        book.setPrice(requestDTO.getPrice());
        book.setDescription(requestDTO.getDescription());
        book.setCartBookQuantity(0);
        return book;
    }

    public JsonResponseDTO deleteBook(String bookName)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Book with name '"+bookName+"' has deleted Successfully");
        responseDTO.setData(null);
        return responseDTO;
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

    public JsonResponseDTO bookUpdated(Book book,String message)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage(message);
        responseDTO.setData(List.of(book));
        return responseDTO;
    }

    public JsonResponseDTO bookFound(Book book)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Book Retrieved SuccessFully");
        responseDTO.setData(List.of(book));
        return responseDTO;
    }

    public JsonResponseDTO bookNotSaved()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage("Book not saved!!");
        responseDTO.setData(null);
        return responseDTO;
    }

    public JsonResponseDTO sendBookList(List<Book> books)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Books retrieved Successfully");
        responseDTO.setData(books);
        return responseDTO;
    }

}
