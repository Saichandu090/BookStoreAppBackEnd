package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.WishListDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishList")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class WishListController
{
    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/addToWishList")
    public ResponseEntity<JsonResponseDTO> addToWishList(@RequestHeader("Authorization")String authHeader, @RequestBody WishListDTO wishListDTO)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return new ResponseEntity<>(wishListService.addToWishList(userDetails.getUsername(),wishListDTO),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/removeFromWishList/{bookId}")
    public ResponseEntity<JsonResponseDTO> removeFromWishList(@RequestHeader("Authorization")String authHeader, @PathVariable Long bookId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(wishListService.removeFromWishList(userDetails.getUsername(),bookId));
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getWishList")
    public ResponseEntity<JsonResponseDTO> getWishList(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(wishListService.getAllWishListItems(userDetails.getUsername()));
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/isInWishList/{bookId}")
    public ResponseEntity<JsonResponseDTO> inWishList(@RequestHeader("Authorization")String authHeader, @PathVariable Long bookId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return ResponseEntity.ok(wishListService.isInWishList(userDetails.getUsername(),bookId));
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(), HttpStatus.BAD_REQUEST);
    }
}
