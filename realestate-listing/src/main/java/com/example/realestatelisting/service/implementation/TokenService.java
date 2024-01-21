package com.example.realestatelisting.service.implementation;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.dto.UpdateRole;

@Service
public class TokenService {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public String generateJWT(Authentication auth) {
        Instant now = Instant.now();

        String userRoles = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .subject(auth.getName())
            .claim("roles", userRoles)
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

    public Map<String,String> validateJWTForRole(String token, UpdateRole body) {
        Map<String, Object> claim = jwtDecoder.decode(token).getClaims();

        if(claim.get("sub").toString().equals(body.getUsername())){
            
            String roles = (String) claim.get("roles");
            
            if(roles.contains(body.getRole())){
                return new HashMap<String, String>(){{
                    put("Message", "Role Already Exists");
                    put("Allow", "false");
                }};
            }

        }
        else{
            return new HashMap<String,String>(){{
                put("Message", "Invalid Username / Wrong Username");
                put("Allow", "false");
        }};
        }
        
        return new HashMap<String,String>(){{
            put("Message", "Valid Request");
            put("Allow", "true");
        }};
    }

    public String validateJWTForUserInfo(String token) {
        return jwtDecoder.decode(token).getSubject();
    }
}
