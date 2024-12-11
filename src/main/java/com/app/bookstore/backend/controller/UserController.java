package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.UserLoginDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserMapper userMapper=new UserMapper();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDTO registerDTO)
    {
        return new ResponseEntity<>(userService.registerUser(registerDTO),HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO loginDTO)
    {
        return new ResponseEntity<>(userService.login(loginDTO),HttpStatus.OK);
    }
}
