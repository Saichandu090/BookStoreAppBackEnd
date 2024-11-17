package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.UserLoginDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.DTO.UserResponseDTO;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.service.JWTService;
import com.app.bookstore.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registerUser")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO)
    {
        return new ResponseEntity<UserResponseDTO>(userService.addUser(userRegisterDTO),HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(),userLoginDTO.getPassword()));
        if(authentication.isAuthenticated())
        {
            return new ResponseEntity<String>(jwtService.generateToken(userLoginDTO.getEmail()),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<String>("Login Failed!!",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email)
    {
        return new ResponseEntity<UserResponseDTO>(userService.findByEmail(email),HttpStatus.FOUND);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers()
    {
        return new ResponseEntity<List<UserResponseDTO>>(userService.findALlUsers(),HttpStatus.FOUND);
    }

    @PatchMapping("/resetPassword/{id}")
    public ResponseEntity<UserResponseDTO> resetPassword(@PathVariable Long id, @RequestBody UserRegisterDTO registerDTO)
    {
        return new ResponseEntity<UserResponseDTO>(userService.resetPassword(id,registerDTO),HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String email,@Valid @RequestBody UserRegisterDTO registerDTO)
    {
        return new ResponseEntity<UserResponseDTO>(userService.updateUser(email,registerDTO),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email)
    {
        return new ResponseEntity<String>(userService.deleteUser(email), HttpStatus.ACCEPTED);
    }
}
