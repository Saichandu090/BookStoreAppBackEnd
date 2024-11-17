package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.exception.InvalidTokenException;
import com.app.bookstore.backend.service.BookService;
import com.app.bookstore.backend.service.JWTService;
import com.app.bookstore.backend.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController
{
    @Autowired
    private BookService bookService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @PostMapping("/addBook")
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody BookRequestDTO bookRequestDTO)
    {
        return new ResponseEntity<BookResponseDTO>(bookService.addBook(bookRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id)
    {
        return new ResponseEntity<BookResponseDTO>(bookService.findById(id),HttpStatus.FOUND);
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<BookResponseDTO> findByName(@PathVariable String name)
    {
        return new ResponseEntity<BookResponseDTO>(bookService.findByName(name),HttpStatus.FOUND);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<List<BookResponseDTO>> findAllBooks()
    {
        return new ResponseEntity<List<BookResponseDTO>>(bookService.getAllBooks(),HttpStatus.FOUND);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,@RequestBody BookRequestDTO requestDTO)
    {
        return new ResponseEntity<BookResponseDTO>(bookService.updateBook(id,requestDTO),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id)
    {
        return new ResponseEntity<String>(bookService.deleteBook(id),HttpStatus.OK);
    }

    @PatchMapping("/changeBookQuantity/{id}")
    public ResponseEntity<BookResponseDTO> updateBookQuantity(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Map<String,Object> fields)
    {
        String token=null;
        String email=null;
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            email=jwtService.extractEmail(token);
        }
        UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
        if(jwtService.validateToken(token,userDetails))
        {
            return new ResponseEntity<BookResponseDTO>(bookService.updateBookQuantity(id,fields),HttpStatus.ACCEPTED);
        }
        else
            throw new InvalidTokenException("Invalid Token");
    }

    @PatchMapping("/changeBookPrice/{id}")
    public ResponseEntity<BookResponseDTO> updateBookPrice(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Map<String,Object> fields)
    {
        String token=null;
        String email=null;
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            email=jwtService.extractEmail(token);
        }
        UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
        if(jwtService.validateToken(token,userDetails))
        {
            return new ResponseEntity<BookResponseDTO>(bookService.updateBookPrice(id,fields),HttpStatus.ACCEPTED);
        }
        else
            throw new InvalidTokenException("Invalid Token");
    }
}
