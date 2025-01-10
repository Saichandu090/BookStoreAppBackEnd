package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.exception.InvalidTokenException;
import com.app.bookstore.backend.mapper.BookMapper;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.service.BookService;
import com.app.bookstore.backend.serviceimpl.BookServiceImpl;
import com.app.bookstore.backend.serviceimpl.JWTService;
import com.app.bookstore.backend.serviceimpl.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class BookController
{
    @Autowired
    private BookService bookService;

    private final BookMapper bookMapper=new BookMapper();

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody BookRequestDTO bookRequestDTO)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(bookService.addBook(bookRequestDTO),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(bookMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> getAllBooks(@RequestHeader("Authorization") String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
        }
        return new ResponseEntity<>(userMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
    }

    @GetMapping("/byBookId/{id}")
    public ResponseEntity<?> getBookById(@RequestHeader("Authorization") String authHeader,@PathVariable Long id)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(bookService.findById(id),HttpStatus.OK);
        }
        return new ResponseEntity<>(userMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
    }

    @GetMapping("/byBookName/{bookName}")
    public ResponseEntity<?> getBookByName(@RequestHeader("Authorization") String authHeader,@PathVariable String bookName)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(bookService.findByName(bookName),HttpStatus.OK);
        }
        return new ResponseEntity<>(userMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
    }

    @PutMapping("/updateBook/{bookId}")
    public ResponseEntity<?> editBook(@RequestHeader("Authorization") String authHeader,@PathVariable Long bookId,@Valid @RequestBody BookRequestDTO bookRequestDTO)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(bookService.updateBook(bookId,bookRequestDTO),HttpStatus.OK);
        }
        return new ResponseEntity<>(bookMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<?> deleteBook(@RequestHeader("Authorization") String authHeader,@PathVariable Long bookId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(bookService.deleteBook(bookId),HttpStatus.OK);
        }
        return new ResponseEntity<>(bookMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/updateBookQuantity/{bookId}")
    public ResponseEntity<?> updateBookQuantity(@RequestHeader("Authorization") String authHeader,@PathVariable Long bookId,@RequestParam int quantity)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(bookService.updateBookQuantity(bookId,quantity),HttpStatus.OK);
        }
        return new ResponseEntity<>(bookMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/updateBookPrice/{bookId}/{bookPrice}")
    public ResponseEntity<?> updateBookPrice(@RequestHeader("Authorization") String authHeader,@PathVariable Long bookId,@PathVariable double bookPrice)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(bookService.updateBookPrice(bookId,bookPrice),HttpStatus.OK);
        }
        return new ResponseEntity<>(bookMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }
}
