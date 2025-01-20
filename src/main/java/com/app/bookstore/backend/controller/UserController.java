package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.UserEditDTO;
import com.app.bookstore.backend.dto.UserLoginDTO;
import com.app.bookstore.backend.dto.UserRegisterDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<JsonResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO registerDTO)
    {
        return new ResponseEntity<>(userService.registerUser(registerDTO),HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<JsonResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO)
    {
        return new ResponseEntity<>(userService.login(loginDTO),HttpStatus.OK);
    }

    @PutMapping("/editUserDetails")
    public ResponseEntity<JsonResponseDTO> editUserDetails(@RequestHeader("Authorization")String authHeader,@Valid @RequestBody UserEditDTO editDTO)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(userService.editUser(userDetails.getUsername(),editDTO),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<JsonResponseDTO> getUserDetails(@RequestHeader("Authorization")String authHeader,@PathVariable String email)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(userService.getUserDetails(email),HttpStatus.OK);
        }
        return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.FORBIDDEN);
    }
}
