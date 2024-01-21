package com.example.realestatelisting.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.repository.UserRepository;
import com.example.realestatelisting.service.UserService;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.UserInfoResponse;


@Service
public class UserServiceImp  implements UserService, UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is Not Found"));
    }

    @Override
    public UserInfoResponse getUser(String username) {
        User user = userRepository.findByUsername(username).get();
        return new UserInfoResponse(user.getUserId(), user.getUsername(), user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }

    @Override
    public UserInfoResponse saveUser(User user) {
        User savedUser = userRepository.save(user);
        return new UserInfoResponse(savedUser.getUserId(), savedUser.getUsername(), savedUser.getFullName(), savedUser.getEmail(), savedUser.getPhoneNumber());
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserInfoResponse getById(String id) {
        User user = userRepository.findById(Long.parseLong(id)).get();
        return new UserInfoResponse(user.getUserId(), user.getUsername(), user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }

    @Override
    public User getEntireUser(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public UserInfoResponse responseConverter(User user) {
        return new UserInfoResponse(user.getUserId(), user.getUsername(), user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }
}