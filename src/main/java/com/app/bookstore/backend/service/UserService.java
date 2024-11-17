package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.UserRegisterDTO;
import com.app.bookstore.backend.DTO.UserResponseDTO;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public UserResponseDTO addUser(UserRegisterDTO userRegisterDTO)
    {
        User user=new User();
        user.setId(userRegisterDTO.getId());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setRegisteredDate(LocalDate.now());
        user.setUpdatedDate(LocalDate.now());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(encoder.encode(userRegisterDTO.getPassword()));
        user.setDob(userRegisterDTO.getDob());
        user.setRole(userRegisterDTO.getRole());

        return userToResponseDTO(userRepository.save(user));
    }

    private UserResponseDTO userToResponseDTO(User user)
    {
        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPassword(user.getPassword());
        responseDTO.setRole(user.getRole());
        return responseDTO;
    }

    public UserResponseDTO updateUser(String email,UserRegisterDTO userRegisterDTO)
    {
        User user=userRepository.findByEmail(email);
        if(user!=null) {
            user.setFirstName(userRegisterDTO.getFirstName());
            user.setLastName(userRegisterDTO.getLastName());
            user.setUpdatedDate(LocalDate.now());
            user.setEmail(userRegisterDTO.getEmail());
            user.setPassword(encoder.encode(userRegisterDTO.getPassword()));
            user.setDob(userRegisterDTO.getDob());
            user.setRole(userRegisterDTO.getRole());
            return userToResponseDTO(userRepository.save(user));
        }
        else
            throw new UserNotFoundException("User Not Found 404");
    }

    public UserResponseDTO findByEmail(String email)
    {
        User user=userRepository.findByEmail(email);
        if(user!=null)
        {
            return userToResponseDTO(user);
        }
        else
            throw new UserNotFoundException("User Not Found 404");
    }

    public List<UserResponseDTO> findALlUsers()
    {
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> responseDTOS=new ArrayList<>();
        for(User user: users)
        {
            responseDTOS.add(userToResponseDTO(user));
        }
        return responseDTOS;
    }

    public String deleteUser(String email)
    {
        User user=userRepository.findByEmail(email);
        if(user!=null) {
            userRepository.delete(user);
            return "User with id " + user.getId() + " has been deleted Successfully";
        }
        else
            throw new UserNotFoundException("User Not Found 404");
    }

    public UserResponseDTO resetPassword(Long id, UserRegisterDTO registerDTO)
    {
        User existingUser=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found 404"));
        existingUser.setPassword(encoder.encode(registerDTO.getPassword()));
        return userToResponseDTO(userRepository.save(existingUser));
    }
}
