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
import com.app.bookstore.backend.repository.CartRepository;
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

    @Autowired
    private CartRepository cartRepository;

    public JsonResponseDTO addBook(BookRequestDTO requestDTO)
    {
        Book book=bookMapper.addBook(requestDTO);
        Book savedBook=bookRepository.save(book);
        return bookMapper.bookSaved(savedBook);
    }

    @Override
    public JsonResponseDTO getAllBooks()
    {
        List<Book> bookList=bookRepository.findAll();
        return bookMapper.sendBookList(bookList);
    }

    @Override
    public JsonResponseDTO findById(Long bookId)
    {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book Not Found"));
        return bookMapper.bookFound(book);
    }

    @Override
    public JsonResponseDTO findByName(String bookName)
    {
        Book book=bookRepository.findByBookName(bookName).orElseThrow(()->new BookNotFoundException("Book Not Found"));
        return bookMapper.bookFound(book);
    }

    @Override
    public JsonResponseDTO updateBook(Long bookId, BookRequestDTO requestDTO)
    {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book Not Found"));
        Book updatedBook=bookMapper.updateBook(book.getBookId(),requestDTO);
        return bookMapper.bookSaved(bookRepository.save(updatedBook));
    }

    @Override
    public JsonResponseDTO deleteBook(Long bookId)
    {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book Not Found"));
        bookRepository.delete(book);
        return bookMapper.deleteBook(book.getBookName());
    }


    @Override
    public JsonResponseDTO updateBookQuantity(Long bookId,int quantity)
    {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not found 404"));
        book.setQuantity(quantity);
//        fields.forEach((key,value)->{
//            Field field=ReflectionUtils.findField(Book.class,key);
//            if(field==null)
//            {
//                throw new BookNotFoundException("Field "+key+" Not found in Book Class");
//            }
//            field.setAccessible(true);
//            ReflectionUtils.setField(field,book,value);
//        });
        Book updatedBook=bookRepository.save(book);
        return bookMapper.bookUpdated(updatedBook,"Book Quantity updated Successfully!!");
    }

    @Override
    public JsonResponseDTO updateBookPrice(Long id,double bookPrice)
    {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Book not found 404"));
        book.setPrice(bookPrice);
//        fields.forEach((key,value)->{
//            Field field=ReflectionUtils.findField(Book.class,key);
//            if(field==null)
//            {
//                throw new BookNotFoundException("Field "+key+" Not found in Book Class");
//            }
//            field.setAccessible(true);
//            ReflectionUtils.setField(field,book,value);
//        });
        Book updatedBook=bookRepository.save(book);
        return bookMapper.bookUpdated(updatedBook,"Book Price Updated Successfully!!");
    }
}
