package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.serviceimpl.JWTService;
import com.app.bookstore.backend.serviceimpl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

public class UserMapper
{
    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;


    //Authorizing User with the Token
    public UserDetails getUserDetails(String authHeader)
    {
        String email=null;

        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            String token=authHeader.substring(7);
            email=jwtService.extractEmail(token);
        }
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            return context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
        }
        else
            return null;
    }

    // Returning in case of Wrong User Details
    public JsonResponseDTO userDetailsFailure()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage("Invalid User Details");
        responseDTO.setData(null);
        return responseDTO;
    }

    //If user Already Exists
    public JsonResponseDTO userAlreadyExists()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage("User Already Exists");
        responseDTO.setData(null);
        return responseDTO;
    }

    // If user try to login without register
    public JsonResponseDTO userNotExists()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage("User not Exists");
        responseDTO.setData(null);
        return responseDTO;
    }

    //
    public JsonResponseDTO loginSuccess(String token)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage(token);
        responseDTO.setData(null);
        return responseDTO;
    }

    // Converting RegisterDTO to User
    public User convertFromRegisterDTO(UserRegisterDTO registerDTO)
    {
        User user=new User();
        user.setUserId(registerDTO.getId());
        user.setEmail(registerDTO.getEmail());
        user.setDob(registerDTO.getDob());
        user.setRegisteredDate(LocalDate.now());
        user.setUpdatedDate(LocalDate.now());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setRole(registerDTO.getRole());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    // Convert User To JsonResponse
    public JsonResponseDTO convertUser(User user)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("User Registered Successfully");
        responseDTO.setData(null);
        return responseDTO;
    }
}
