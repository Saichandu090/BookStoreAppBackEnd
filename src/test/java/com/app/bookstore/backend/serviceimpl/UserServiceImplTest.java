package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.UserService;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest
{
    @InjectMocks
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    private final UserMapper userMapper=new UserMapper();

    @Test
    public void registerUser()
    {
        UserRegisterDTO registerDTO=new UserRegisterDTO("Marri","Sai chandu", LocalDate.of(2002,8,24),"saichandu@090","marrisaichandu143@gmail.com","ADMIN");
        User user=userMapper.convertFromRegisterDTO(registerDTO);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        JsonResponseDTO response = userService.registerUser(registerDTO);

        assertTrue(response.isResult());

        Mockito.verify(userRepository).save(user);
    }
}