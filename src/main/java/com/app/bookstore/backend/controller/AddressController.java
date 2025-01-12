package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class AddressController
{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress")
    public ResponseEntity<JsonResponseDTO> addAddress(@RequestHeader("Authorization")String authHeader, @RequestBody Address address)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressService.addAddress(userDetails.getUsername(),address), HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/editAddress/{addressId}")
    public ResponseEntity<JsonResponseDTO> editAddress(@RequestHeader("Authorization")String authHeader,@PathVariable Long addressId, @RequestBody Address address)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressService.editAddress(userDetails.getUsername(),addressId,address), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<JsonResponseDTO> deleteAddress(@RequestHeader("Authorization")String authHeader,@PathVariable Long addressId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressService.deleteAddress(userDetails.getUsername(),addressId), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/allAddress")
    public ResponseEntity<JsonResponseDTO> getAllUserAddress(@RequestHeader("Authorization")String authHeader)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressService.getAllUserAddress(userDetails.getUsername()), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAddressById/{addressId}")
    public ResponseEntity<JsonResponseDTO> getUserAddressById(@RequestHeader("Authorization")String authHeader,@PathVariable Long addressId)
    {
        UserDetails userDetails=userMapper.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressService.getAddressById(userDetails.getUsername(),addressId), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(userMapper.noAuthority(),HttpStatus.BAD_REQUEST);
    }
}
