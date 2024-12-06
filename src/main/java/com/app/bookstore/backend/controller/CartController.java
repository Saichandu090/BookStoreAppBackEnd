package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private CartService cartService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization")String authHeader, @RequestBody CartRequestDTO requestDTO)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.addToCart(userDetails.getUsername(),requestDTO));
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getCart")
    public ResponseEntity<?> getUserCart(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.getUserCart(userDetails.getUsername()));
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeFromCart/{bookId}")
    public ResponseEntity<?> removeFromCart(@RequestHeader("Authorization")String authHeader,@PathVariable Long bookId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.removeFromCart(userDetails.getUsername(),bookId));
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/clearCart")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.clearCart(userDetails.getUsername()));
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }
}
