package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.UserEditDTO;
import com.app.bookstore.backend.dto.UserLoginDTO;
import com.app.bookstore.backend.dto.UserRegisterDTO;

public interface UserService
{
    JsonResponseDTO registerUser(UserRegisterDTO registerDTO);

    JsonResponseDTO login(UserLoginDTO loginDTO);

    JsonResponseDTO editUser(String email, UserEditDTO editDTO);

    JsonResponseDTO getUserDetails(String email);
}
