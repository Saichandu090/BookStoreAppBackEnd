package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookService
{
    @Autowired
    private BookRepository bookRepository;

    public BookResponseDTO addBook(BookRequestDTO requestDTO)
    {
        Book book=new Book();
        book.setId(requestDTO.getId());
        book.setName(requestDTO.getName());
        book.setAuthor(requestDTO.getAuthor());
        book.setPrice(requestDTO.getPrice());
        book.setQuantity(requestDTO.getQuantity());
        book.setDescription(requestDTO.getDescription());

        return bookToResponseDTO(bookRepository.save(book));
    }

    private BookResponseDTO bookToResponseDTO(Book book)
    {
        BookResponseDTO responseDTO=new BookResponseDTO();
        responseDTO.setId(book.getId());
        responseDTO.setName(book.getName());
        responseDTO.setAuthor(book.getAuthor());
        responseDTO.setPrice(book.getPrice());
        responseDTO.setDescription(book.getDescription());
        responseDTO.setQuantity(book.getQuantity());
        return responseDTO;
    }

    public BookResponseDTO findById(Long id)
    {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book Not Found 404"));
        return bookToResponseDTO(book);
    }

    public BookResponseDTO findByName(String name)
    {
        Book book=bookRepository.findByName(name);
        if(book==null)
            throw new BookNotFoundException("Book not found 404");
        else
            return bookToResponseDTO(book);
    }

    public List<BookResponseDTO> getAllBooks()
    {
        List<Book> books=bookRepository.findAll();
        List<BookResponseDTO> responseDTOS=new ArrayList<>();
        for(Book book : books)
        {
            responseDTOS.add(bookToResponseDTO(book));
        }
        return responseDTOS;
    }

    public BookResponseDTO updateBook(Long id,BookRequestDTO requestDTO)
    {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book Not Found 404"));
        book.setName(requestDTO.getName());
        book.setAuthor(requestDTO.getAuthor());
        book.setPrice(requestDTO.getPrice());
        book.setQuantity(requestDTO.getQuantity());
        book.setDescription(requestDTO.getDescription());

        return bookToResponseDTO(bookRepository.save(book));
    }

    public String deleteBook(Long id)
    {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book Not Found 404"));
        bookRepository.delete(book);
        return "Book with id "+id+" has been deleted from the Book Store.";
    }

    public BookResponseDTO updateBookQuantity(Long id,Map<String,Object> fields)
    {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book not found 404"));

        fields.forEach((key,value)->{
            Field field=ReflectionUtils.findField(Book.class,key);
            if(field==null)
            {
                throw new BookNotFoundException("Field "+key+" Not found in Book Class");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field,book,value);
        });
        return bookToResponseDTO(bookRepository.save(book));
    }

    public BookResponseDTO updateBookPrice(Long id, Map<String,Object> fields)
    {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book not found 404"));

        fields.forEach((key,value)->{
            Field field=ReflectionUtils.findField(Book.class,key);
            if(field==null)
            {
                throw new BookNotFoundException("Field "+key+" Not found in Book Class");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field,book,value);
        });
        return bookToResponseDTO(bookRepository.save(book));
    }
}
