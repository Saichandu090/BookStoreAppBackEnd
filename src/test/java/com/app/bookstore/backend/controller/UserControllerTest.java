package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.UserLoginDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    AuthenticationManager authenticationManager;

    @MockBean
    UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Test
    public void registerUserTest() throws  Exception
    {
        UserRegisterDTO input=new UserRegisterDTO("First","Last",LocalDate.of(2002,5,4),"testing@123","test@gmail.com","USER");
        String requestBody=objectMapper.writeValueAsString(input);

        JsonResponseDTO dto=new JsonResponseDTO(true,"",null);

        when(userService.registerUser(input))
                .thenReturn(dto);

        assertTrue(dto.isResult());

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        System.out.println(objectMapper.writeValueAsString(input));
    }

    @Test
    public void loginTest() throws Exception
    {
        UserLoginDTO dto=new UserLoginDTO("test@gmail.com","testing@123");
        String reqBody=objectMapper.writeValueAsString(dto);

        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO(true,"",null);

        when(userService.login(dto))
                .thenReturn(jsonResponseDTO);

        assertTrue(userService.login(dto).isResult());

        System.out.println(objectMapper.writeValueAsString(jsonResponseDTO));
    }
}