package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
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
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class CartController
{
    @Autowired
    private CartService cartService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/addToCart")
    public ResponseEntity<JsonResponseDTO> addToCart(@RequestHeader("Authorization")String authHeader, @RequestBody CartRequestDTO requestDTO)
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
    public ResponseEntity<JsonResponseDTO> getUserCart(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.getUserCarts(userDetails.getUsername()));
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCartById/{id}")
    public ResponseEntity<JsonResponseDTO> getUserCartById(@RequestHeader("Authorization")String authHeader,@PathVariable Long id)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.getUserCartById(userDetails.getUsername(),id));
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeFromCart/{cartId}")
    public ResponseEntity<JsonResponseDTO> removeFromCart(@RequestHeader("Authorization")String authHeader,@PathVariable Long cartId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(cartService.removeFromCart(userDetails.getUsername(),cartId));
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/clearCart")
    public ResponseEntity<JsonResponseDTO> clearCart(@RequestHeader("Authorization")String authHeader)
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

    @GetMapping("/getAllCarts")
    public ResponseEntity<JsonResponseDTO> getAllCarts(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return ResponseEntity.ok(cartService.getAllCarts());
        }
        else
        {
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
        }
    }
}
