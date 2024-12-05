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
@CrossOrigin("*")
public class BookController
{
    @Autowired
    private BookService bookService;

    @Autowired
    private JWTService jwtService;

    private final BookMapper bookMapper=new BookMapper();

    private final UserMapper userMapper=new UserMapper();

    @Autowired
    ApplicationContext context;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestHeader("Authorization") String authHeader,@RequestBody BookRequestDTO bookRequestDTO)
    {
        UserDetails userDetails=userMapper.getUserDetails(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(bookService.addBook(userDetails.getUsername(),bookRequestDTO), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(bookMapper.noAuthority(),HttpStatus.FORBIDDEN);
        }
    }

}
