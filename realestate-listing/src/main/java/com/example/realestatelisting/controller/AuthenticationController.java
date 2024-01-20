package com.example.realestatelisting.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.AgentProfileResponse;
import com.example.realestatelisting.models.dto.LoginResponse;
import com.example.realestatelisting.models.dto.Registration;
import com.example.realestatelisting.models.dto.UpdateRole;
import com.example.realestatelisting.service.implementation.AgentProfileServiceImp;
import com.example.realestatelisting.service.implementation.AuthenticationService;
import com.example.realestatelisting.service.implementation.TokenService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AgentProfileServiceImp agentProfileService;

    @PostMapping("/agent-registration")
    public ResponseEntity<AgentProfileResponse> registUserAsAgent(@RequestBody Registration body) {
        User newUser = authenticationService.registerUser(body.getUsername(), body.getPassword(),"AGENT");
        AgentProfileResponse newProfile = agentProfileService.createAgent(newUser);
        return new ResponseEntity<AgentProfileResponse>(newProfile, HttpStatus.OK);
    }

    @PostMapping("/buyer-registration")
    public ResponseEntity<User> registUserAsBuyer(@RequestBody Registration body) {
        return new ResponseEntity<User>(authenticationService.registerUser(body.getUsername(), body.getPassword(),"BUYER"), HttpStatus.OK);

    }

    @PostMapping("/seller-registration")
    public ResponseEntity<User> registUserAsSeller(@RequestBody Registration body) {
        return new ResponseEntity<User>(authenticationService.registerUser(body.getUsername(), body.getPassword(),"SELLER"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody Registration body) {
        return new ResponseEntity<LoginResponse>(authenticationService.loginUser(body.getUsername(), body.getPassword()), HttpStatus.OK);
    }
    
    @PutMapping("/add-role")
    public ResponseEntity<User> additionalRole(@RequestBody UpdateRole body, HttpServletRequest request) {

        Map<String,String> validatoryResponse = tokenService.validateJWTForRole(request.getHeader("Authorization").split(" ",2)[1],body);
        
        if(validatoryResponse.get("Allow").equals("true")){
            return new ResponseEntity<>(authenticationService.addNewRole(body.getUsername(), body.getRole()),HttpStatus.OK);
        }
        else{
            System.out.println(validatoryResponse.get("Message"));
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }

    }

    @PutMapping("/remove-role")
    public ResponseEntity<User> removalRole(@RequestBody UpdateRole body, HttpServletRequest request) {

        Map<String,String> validatoryResponse = tokenService.validateJWTForRole(request.getHeader("Authorization").split(" ",2)[1],body);
        
        if(validatoryResponse.get("Allow").equals("false") && validatoryResponse.get("Message").equals("Role Already Exists")){
            return new ResponseEntity<>(authenticationService.removeRole(body.getUsername(), body.getRole()),HttpStatus.OK);
        }
        else{
            System.out.println(validatoryResponse.get("Message"));
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }

    }

}
