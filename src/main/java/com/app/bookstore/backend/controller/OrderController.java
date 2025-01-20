package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.OrderDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/placeOrder")
    public ResponseEntity<JsonResponseDTO> placeOrder(@RequestHeader("Authorization")String authHeader, @RequestBody OrderDTO orderDTO)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return new ResponseEntity<>(orderService.placeOrder(userDetails.getUsername(),orderDTO), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<JsonResponseDTO> cancelOrder(@RequestHeader("Authorization")String authHeader,@PathVariable Long orderId)
    {
        System.out.println(orderId);
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return new ResponseEntity<>(orderService.cancelOrder(userDetails.getUsername(),orderId),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAllUserOrders")
    public ResponseEntity<JsonResponseDTO> getAllOrdersForUser(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails= userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
        {
            return new ResponseEntity<>(orderService.getAllOrdersForUser(userDetails.getUsername()),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<JsonResponseDTO> getAllOrders(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails= userMapper.validateUserToken(authHeader);
        if(userDetails!=null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }
}
