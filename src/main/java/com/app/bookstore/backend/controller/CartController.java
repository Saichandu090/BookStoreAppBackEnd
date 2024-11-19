package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.CartResponseDTO;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.service.CartService;
import com.app.bookstore.backend.service.JWTService;
import com.app.bookstore.backend.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private CartService cartService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @PostMapping("/addBook")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestHeader("Authorization") String authHeader, @RequestBody CartRequestDTO requestDTO)
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
            return new ResponseEntity<>(cartService.addToCart(userDetails,requestDTO), HttpStatus.ACCEPTED);
        }
        else
            throw new UserNotFoundException("User Not Found 404");
    }

    @GetMapping("/allCarts")
    public ResponseEntity<List<CartResponseDTO>> getAllCarts()
    {
        return new ResponseEntity<>(cartService.getAllCarts(),HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteCartById/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id)
    {
        return new ResponseEntity<>(cartService.removeFromCart(id),HttpStatus.OK);
    }

    @DeleteMapping("/deleteMyCart")
    public ResponseEntity<String> deleteUserCart(@RequestHeader("Authorization")String authHeader)
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
            return new ResponseEntity<>(cartService.deleteUserCart(userDetails),HttpStatus.OK);
        }
        else
            throw new UserNotFoundException("User Not Found 404");
    }

    @GetMapping("/getALlMyItems")
    public ResponseEntity<CartResponseDTO> getALlUserItems(@RequestHeader("Authorization")String authHeader)
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
            return new ResponseEntity<>(cartService.getALlUserItems(userDetails),HttpStatus.OK);
        }
        else
            throw new UserNotFoundException("User Not Found 404");
    }
}
