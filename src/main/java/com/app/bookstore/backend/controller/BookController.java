package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController
{
    @Autowired
    private BookService bookService;

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
}
