package com.example.realestatelisting.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.UserInfoResponse;

@Service
public interface UserService {
    public User getEntireUser(String username);
    public UserInfoResponse getUser(String username);
    public UserInfoResponse saveUser(User user);
    public List<User> getAll();
    public UserInfoResponse getById(String id);
    public UserInfoResponse responseConverter(User user);
    public Page<User> getAllPageable(Integer page);
}
