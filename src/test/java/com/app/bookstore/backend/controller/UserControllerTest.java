package com.app.bookstore.backend.controller;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.UserEditDTO;
import com.app.bookstore.backend.DTO.UserLoginDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.config.SecurityConfig;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.service.UserService;
import com.app.bookstore.backend.serviceimpl.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityConfig.class)
class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserDetails userDetails;

    @Test
    public void UserController_RegisterUser_ReturnTrue() throws Exception
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO(true,"User Registered Successfully",null);
        UserRegisterDTO registerDTO=UserRegisterDTO.builder()
                .firstName("Jenny")
                        .lastName("Lamgade")
                                .dob(LocalDate.of(2002,8,24))
                                        .email("jenny090@gmail.com")
                                                .password("jenny@090")
                                                        .role("USER").build();
        given(userService.registerUser(ArgumentMatchers.any())).willReturn(responseDTO);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO))
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void UserController_LoginUser_ReturnToken() throws Exception
    {
        UserLoginDTO loginDTO=new UserLoginDTO("jenny090@gmail.com","jenny@090");
        String expectedToken="jwt-secret token";

        JsonResponseDTO responseDTO=new JsonResponseDTO(true,expectedToken,null);
        given(userService.login(ArgumentMatchers.any())).willReturn(responseDTO);
        given(jwtService.generateToken(ArgumentMatchers.anyString()))
                .willReturn(expectedToken);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is(expectedToken)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result",CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void UserController_EditUserDetails_ShouldEdit() throws Exception
    {
        UserEditDTO editDTO=new UserEditDTO("Sai","Chandu",LocalDate.of(2005,8,24));
        JsonResponseDTO responseDTO=new JsonResponseDTO(true,"User edited successfully",null);

        String token="Bearer token";
        given(jwtService.validateToken(ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(true);
        given(userMapper.validateUserToken(ArgumentMatchers.any())).willReturn(userDetails);
        given(userService.editUser(ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(responseDTO);

        mockMvc.perform(put("/editUserDetails")
                .header("Authorization",token)
                        .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result",CoreMatchers.is(true)));
    }

    @Test
    public void UserController_GetUser_ShouldReturnUser() throws Exception
    {
        String token="Bearer token";
        String email="jenny090@gmail.com";
        UserEditDTO editDTO=new UserEditDTO();
        JsonResponseDTO responseDTO=new JsonResponseDTO(true,"Got the user",List.of(editDTO));

        given(userService.getUserDetails(ArgumentMatchers.any())).willReturn(responseDTO);
        given(userMapper.validateUserToken(ArgumentMatchers.any())).willReturn(userDetails);

        mockMvc.perform(get("/getUser/{email}",email)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",CoreMatchers.is(responseDTO.getMessage())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].firstName",CoreMatchers.is(editDTO.getFirstName())))
                .andDo(MockMvcResultHandlers.print());
    }
}