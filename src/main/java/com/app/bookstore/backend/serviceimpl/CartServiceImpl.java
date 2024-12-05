package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.CartRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookRepository bookRepository;

}
