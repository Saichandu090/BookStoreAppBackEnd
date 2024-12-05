package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.UserLoginDTO;
import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserMapper userMapper=new UserMapper();

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Override
    public JsonResponseDTO registerUser(UserRegisterDTO registerDTO)
    {
        boolean isUserExist=userRepository.existsByEmail(registerDTO.getEmail());
        if(isUserExist){
            return userMapper.userAlreadyExists();
        }
        User newUser=userMapper.convertFromRegisterDTO(registerDTO);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        User savedUser=userRepository.save(newUser);
        return userMapper.convertUser(savedUser);
    }

    @Override
    public JsonResponseDTO login(UserLoginDTO loginDTO)
    {
        boolean isUserExists=userRepository.existsByEmail(loginDTO.getEmail());
        if(isUserExists)
        {
            Authentication authentication=
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
            if(authentication.isAuthenticated())
            {
                String token=jwtService.generateToken(loginDTO.getEmail());
                return userMapper.loginSuccess(token);
            }
        }
        return userMapper.userNotExists();
    }
}
