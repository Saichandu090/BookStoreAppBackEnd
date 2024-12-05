package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.UserLoginDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;

public interface UserService
{
    JsonResponseDTO registerUser(UserRegisterDTO registerDTO);

    JsonResponseDTO login(UserLoginDTO loginDTO);
}
