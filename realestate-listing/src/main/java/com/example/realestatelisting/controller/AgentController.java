package com.example.realestatelisting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.AgentAssociationResponse;
import com.example.realestatelisting.models.dto.AgentProfileResponse;
import com.example.realestatelisting.models.dto.Price;
import com.example.realestatelisting.models.dto.Rating;
import com.example.realestatelisting.service.implementation.AgentAssociationServiceImp;
import com.example.realestatelisting.service.implementation.AgentProfileServiceImp;
import com.example.realestatelisting.service.implementation.TokenService;
import com.example.realestatelisting.service.implementation.UserServiceImp;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/agent")
@CrossOrigin("*")
public class AgentController {
    
    @Autowired
    TokenService tokenService;

    @Autowired
    AgentProfileServiceImp agentProfileService;

    @Autowired
    AgentAssociationServiceImp agentAssociationService;

    @Autowired
    UserServiceImp userService;

    @GetMapping("/")
    public ResponseEntity<List<AgentProfileResponse>> getAllAgents() {
        return new ResponseEntity<>(agentProfileService.getAllProfiles(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentProfileResponse> getAgentInfo(@PathVariable String id) {
        AgentProfileResponse profile = agentProfileService.getAgentProfile(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
    
    @PostMapping("/select-agent/{id}")
    public ResponseEntity<AgentAssociationResponse> selectAgent(@PathVariable String id, @RequestBody Price body, HttpServletRequest request) {
        AgentProfile agent = agentProfileService.getAgentEntireProfile(id);
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getEntireUser(username);
        return new ResponseEntity<>(agentAssociationService.saveAssociation(user, agent, body.getPrice()), HttpStatus.OK);
    }
    
    @PostMapping("/rate-agent/{id}")
    public ResponseEntity<AgentProfileResponse> rateAgent(@PathVariable String id, @RequestBody Rating body) {
        AgentProfile agent = agentProfileService.getAgentEntireProfile(id);
        agent.setRating(body.getRating());
        return new ResponseEntity<>(agentProfileService.saveAgent(agent),HttpStatus.OK);
    }
}
