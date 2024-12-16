package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest
{
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    public void deleteBookTest() throws BookNotFoundException
    {
        Book book=new Book();
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        Assertions.assertNotNull(bookService.deleteBook(1L));
    }

    @Test
    public void deleteBookTest_BookNotFoundException()
    {
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));
        Assertions.assertThrows(BookNotFoundException.class,()->bookService.deleteBook(1L));
    }

    @Test
    public void updateBookTest() throws BookNotFoundException
    {
        
    }
}