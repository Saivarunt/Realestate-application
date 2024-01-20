package com.example.realestatelisting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Getter
    @Setter
    private String username;
    
    @Getter
    @Setter
    private String password;

    public String infoVerify(){
        return "Username : " + this.username + "Password : " + this.password;
    }
}
