package com.app.bookstore.backend.service;

import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.model.UserPrinciple;
import com.app.bookstore.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user=userRepository.findByEmail(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("User Not Found!!");
        }
        return new UserPrinciple(user);
    }
}
