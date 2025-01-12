package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.LoginResponseDTO;
import com.app.bookstore.backend.DTO.UserEditDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.serviceimpl.JWTService;
import com.app.bookstore.backend.serviceimpl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper
{
    @Autowired
    JWTService jwtService;

    @Autowired
    ApplicationContext context;

    //Authorizing User with the Token
    public UserDetails validateUserToken(String authHeader)
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
            return userDetails;
        else
            return null;
    }

    // Returning in case of Wrong User Details
    public JsonResponseDTO userDetailsFailure()
    {
        return JsonResponseDTO.builder()
                .result(false)
                .message("Invalid User Details")
                .data(null).build();
    }

    public JsonResponseDTO noAuthority()
    {
        return JsonResponseDTO.builder()
                .result(false)
                .message("No Authority")
                .data(null).build();
    }

    //If user Already Exists
    public JsonResponseDTO userAlreadyExists()
    {
        return JsonResponseDTO.builder()
                .result(false)
                .message("User Already Exists")
                .data(null).build();
    }

    // If user try to log in without register
    public JsonResponseDTO userNotExists()
    {
        return JsonResponseDTO.builder()
                .result(false)
                .message("User not Exists")
                .data(null).build();
    }

    public JsonResponseDTO loginSuccess(String token,String email,String role)
    {
        List<LoginResponseDTO> list=new ArrayList<>();
        list.add(new LoginResponseDTO(email,role));

        return JsonResponseDTO.builder()
                .result(true)
                .message(token)
                .data(list).build();
    }

    // Converting RegisterDTO to User
    public User convertFromRegisterDTO(UserRegisterDTO registerDTO)
    {
        return User.builder()
                .email(registerDTO.getEmail())
                .dob(registerDTO.getDob())
                .registeredDate(LocalDate.now())
                .updatedDate(null)
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .role(registerDTO.getRole())
                .password(registerDTO.getPassword()).build();
    }

    // Convert User To JsonResponse
    public JsonResponseDTO convertUser(String message)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .message(message)
                .data(null).build();
    }

    public User editUser(User user,UserEditDTO editDTO)
    {
        user.setFirstName(editDTO.getFirstName());
        user.setLastName(editDTO.getLastName());
        user.setDob(editDTO.getDob());
        user.setUpdatedDate(LocalDate.now());
        return user;
    }

    public JsonResponseDTO returnUser(User user)
    {
        UserEditDTO editDTO=UserEditDTO.builder()
                        .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                        .dob(user.getDob()).build();
        return JsonResponseDTO.builder()
                .result(true)
                .data(List.of(editDTO))
                .message(null).build();
    }
}
