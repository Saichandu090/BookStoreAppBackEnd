package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.BookMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookRepository bookRepository;

    private final BookMapper bookMapper=new BookMapper();

    @Autowired
    private UserRepository userRepository;

    public JsonResponseDTO addBook(String email,BookRequestDTO requestDTO)
    {
        Book book=bookMapper.addBook(requestDTO);
        Book savedBook=bookRepository.save(book);
        return bookMapper.bookSaved(savedBook);
    }

    @Override
    public JsonResponseDTO getBooks(String email)
    {
        return null;
    }

    @Override
    public JsonResponseDTO findById(String email, Long bookId)
    {
        return null;
    }

    @Override
    public JsonResponseDTO findByName(String email, String bookName)
    {
        return null;
    }

    @Override
    public JsonResponseDTO updateBook(String email, Long bookId, BookRequestDTO requestDTO)
    {
        return null;
    }

    @Override
    public JsonResponseDTO deleteBook(String email, Long bookId)
    {
        return null;
    }


//    public BookResponseDTO updateBookQuantity(Long id,Map<String,Object> fields)
//    {
//        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book not found 404"));
//
//        fields.forEach((key,value)->{
//            Field field=ReflectionUtils.findField(Book.class,key);
//            if(field==null)
//            {
//                throw new BookNotFoundException("Field "+key+" Not found in Book Class");
//            }
//            field.setAccessible(true);
//            ReflectionUtils.setField(field,book,value);
//        });
//        return bookToResponseDTO(bookRepository.save(book));
//    }
//
//    public BookResponseDTO updateBookPrice(Long id, Map<String,Object> fields)
//    {
//        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book not found 404"));
//
//        fields.forEach((key,value)->{
//            Field field=ReflectionUtils.findField(Book.class,key);
//            if(field==null)
//            {
//                throw new BookNotFoundException("Field "+key+" Not found in Book Class");
//            }
//            field.setAccessible(true);
//            ReflectionUtils.setField(field,book,value);
//        });
//        return bookToResponseDTO(bookRepository.save(book));
//    }
}
