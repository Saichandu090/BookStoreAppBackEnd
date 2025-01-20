package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.UserEditDTO;
import com.app.bookstore.backend.dto.UserLoginDTO;
import com.app.bookstore.backend.dto.UserRegisterDTO;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;
    private JWTService jwtService;
    ApplicationContext context;
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
        return userMapper.convertUser("User Registered Successfully!!");
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
                UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(loginDTO.getEmail());
                String role=null;
                if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
                    role="USER";
                else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
                    role="ADMIN";
                return userMapper.loginSuccess(token,loginDTO.getEmail(),role);
            }
        }
        return userMapper.userNotExists();
    }

    @Override
    public JsonResponseDTO editUser(String email, UserEditDTO editDTO)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        User editedUser=userMapper.editUser(user,editDTO);
        User res=userRepository.save(editedUser);

        return userMapper.convertUser("User details edited Successfully!!");
    }

    @Override
    public JsonResponseDTO getUserDetails(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        return userMapper.returnUser(user);
    }
}
