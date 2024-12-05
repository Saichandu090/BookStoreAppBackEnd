package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.CartResponseDTO;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.serviceimpl.CartServiceImpl;
import com.app.bookstore.backend.serviceimpl.JWTService;
import com.app.bookstore.backend.serviceimpl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

}
