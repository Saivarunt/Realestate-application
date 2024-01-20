package com.example.realestatelisting.models.dto;

import com.example.realestatelisting.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private String jwt;
}
