package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.UserLoginDTO;
import com.app.bookstore.backend.dto.UserRegisterDTO;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.serviceimpl.JWTService;
import com.app.bookstore.backend.serviceimpl.MyUserDetailsService;
import com.app.bookstore.backend.serviceimpl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ApplicationContext context;

    private UserDetails userDetails;
    @Mock
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserRegisterDTO userRegisterDTO;
    private UserLoginDTO loginDTO;

    @BeforeEach
    public void init()
    {
        userRegisterDTO=UserRegisterDTO.builder()
                .firstName("Sai")
                .lastName("Chandu")
                .email("sai@gmail.com")
                .dob(LocalDate.of(2002,8,24))
                .password("saichandu@090")
                .role("ADMIN").build();

        user=User.builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .dob(userRegisterDTO.getDob())
                .password(userRegisterDTO.getPassword())
                .role(userRegisterDTO.getRole())
                .registeredDate(LocalDate.now()).build();

        loginDTO=UserLoginDTO.builder()
                .email("sai@gmail.com")
                .password("saichandu@090").build();

        userDetails=new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(new SimpleGrantedAuthority("USER"));
            }

            @Override
            public String getPassword() {
                return loginDTO.getPassword();
            }

            @Override
            public String getUsername() {
                return loginDTO.getEmail();
            }
        };
    }

    @Test
    public void UserService_RegisterUser_MustRegisterUser()
    {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        JsonResponseDTO responseDTO=userService.registerUser(userRegisterDTO);

        System.out.println(responseDTO);
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.isResult()).isEqualTo(true);
    }

    @Test
    public void UserService_LoginUser_MustLogin()
    {
        when(userRepository.existsByEmail(loginDTO.getEmail())).thenReturn(true);

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword());
        Authentication authenticationResult=Mockito.mock(Authentication.class);

        when(authenticationResult.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationResult);

        when(jwtService.generateToken(loginDTO.getEmail())).thenReturn("jwt-token");

        when(context.getBean(MyUserDetailsService.class)).thenReturn(myUserDetailsService);
        when(myUserDetailsService.loadUserByUsername(loginDTO.getEmail())).thenReturn(userDetails);

        JsonResponseDTO responseDTO=userService.login(loginDTO);

        System.out.println(responseDTO);
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.isResult()).isEqualTo(true);
    }
}