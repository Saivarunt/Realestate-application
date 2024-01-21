package com.example.realestatelisting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserInfo {
    private String username;

    private String fullname;

    private String email;

    private String phonenumber;
}
