package com.example.realestatelisting.service.implementation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.Role;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.LoginResponse;
import com.example.realestatelisting.repository.RoleRepositoy;
import com.example.realestatelisting.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepositoy roleRepositoy;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User registerUser(String username, String password, String role){
        
        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleRepositoy.findByAuthority("USER").get();

        Role subRole = null;
        
        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        if(role.equals("BUYER")){
            subRole = roleRepositoy.findByAuthority("BUYER").get();
        }
        else if(role.equals("SELLER")){
            subRole = roleRepositoy.findByAuthority("SELLER").get();
        }
        else if(role.equals("AGENT")){
            subRole = roleRepositoy.findByAuthority("AGENT").get();
        }
        else{
            throw new RuntimeException("Invalid role details.");
        }
        if(subRole != null){
            authorities.add(subRole);
        }

        return userRepository.save(new User((long) 0, username, encodedPassword, authorities,"","",""));
    }

    public LoginResponse loginUser(String username, String password){
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = tokenService.generateJWT(auth);
        return new LoginResponse(userRepository.findByUsername(username).get(), token);

    }


    public User addNewRole(String username, String newRole){

        Role subRole = null;
        Optional<User> user = userRepository.findByUsername(username);
        User updatedUser = null;

        if(newRole.equals("BUYER")){
            subRole = roleRepositoy.findByAuthority("BUYER").get();
        }
        else if(newRole.equals("SELLER")){
            subRole = roleRepositoy.findByAuthority("SELLER").get();
        }
        else if(newRole.equals("AGENT")){
            subRole = roleRepositoy.findByAuthority("AGENT").get();
        }
        else{
            throw new RuntimeException("Invalid role details.");
        }
        if(subRole != null){
           updatedUser = user.get().addAuthorities(subRole);
        }
        return userRepository.save(updatedUser);
    }


    
    public User removeRole(String username, String role){

        Role subRole = null;
        Optional<User> user = userRepository.findByUsername(username);
        User updatedUser = null;

        if(role.equals("BUYER")){
            subRole = roleRepositoy.findByAuthority("BUYER").get();
        }
        else if(role.equals("SELLER")){
            subRole = roleRepositoy.findByAuthority("SELLER").get();
        }
        else if(role.equals("AGENT")){
            subRole = roleRepositoy.findByAuthority("AGENT").get();
        }
        else{
            throw new RuntimeException("Invalid role details.");
        }
        if(subRole != null){
           updatedUser = user.get().updateAuthorities(subRole);
        }
        return userRepository.save(updatedUser);
    }
}
