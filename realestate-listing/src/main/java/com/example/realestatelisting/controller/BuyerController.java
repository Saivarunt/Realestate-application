package com.example.realestatelisting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.PropertyWithDeadline;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PurchaseInfoResponse;
import com.example.realestatelisting.models.dto.SelectedAgent;
import com.example.realestatelisting.service.implementation.AgentAssociationServiceImp;
import com.example.realestatelisting.service.implementation.AgentProfileServiceImp;
import com.example.realestatelisting.service.implementation.PropertyDetailsServiceImp;
import com.example.realestatelisting.service.implementation.PropertyWithDeadlineServiceImp;
import com.example.realestatelisting.service.implementation.PurchaseInfoServiceImp;
import com.example.realestatelisting.service.implementation.TokenService;
import com.example.realestatelisting.service.implementation.UserServiceImp;

import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/buyer")
@CrossOrigin("*")
public class BuyerController {

    @Autowired
    PropertyWithDeadlineServiceImp propertyWithDeadlineService;
    
    @Autowired
    PropertyDetailsServiceImp propertyDetailsService;

    @Autowired
    UserServiceImp userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AgentAssociationServiceImp agentAssociationService;

    @Autowired
    PurchaseInfoServiceImp purchaseInfoService;

    @Autowired
    AgentProfileServiceImp agentProfileService;

    @PostMapping("/buy-property/{id}")
    public ResponseEntity<PurchaseInfoResponse> purchaseProperty(@RequestBody SelectedAgent body, @PathVariable String id, HttpServletRequest request) {
        PropertyDetails property = propertyDetailsService.getEntireDetailsById(id);

        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);

        if(property.getAvailability()){
            User buyer = userService.getEntireUser(username);

            AgentProfile agent = agentAssociationService.findAssociation(buyer, body.getAgentId());
            property.setAvailability(false);
            propertyDetailsService.save(property);
            
            property = propertyDetailsService.getEntireDetailsById(id);

            PurchaseInfoResponse purchaseInfo = purchaseInfoService.savePurchaseInfo(buyer, agent, property);
            
            agent.setSale_count(agent.getSale_count() + 1);
            
            agentProfileService.saveAgent(agent,null);

            return new ResponseEntity<>(purchaseInfo,HttpStatus.OK); 
        }

        return new ResponseEntity<>(null,HttpStatus.ALREADY_REPORTED);
    }

    @PostMapping("/buy-property-with-deadline/{id}")
    public ResponseEntity<PurchaseInfoResponse> purchasePropertyWithDeadline(@RequestBody SelectedAgent body, @PathVariable String id, HttpServletRequest request) {
        PropertyDetails property = propertyDetailsService.getEntireDetailsById(id);

        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);

        PropertyWithDeadline propertyWithDeadline = propertyWithDeadlineService.getByProperty(property);

        Date date1 = new Date();

        if(property.getAvailability() && (propertyWithDeadline.getStart_date().equals(date1) || propertyWithDeadline.getStart_date().after(date1)) && (propertyWithDeadline.getEnd_date().before(date1)) ){
            User buyer = userService.getEntireUser(username);

            AgentProfile agent = agentAssociationService.findAssociation(buyer, body.getAgentId());
            property.setAvailability(false);
            propertyDetailsService.save(property);
            
            property = propertyDetailsService.getEntireDetailsById(id);

            PurchaseInfoResponse purchaseInfo = purchaseInfoService.savePurchaseInfo(buyer, agent, property);
            
            agent.setSale_count(agent.getSale_count() + 1);
            
            agentProfileService.saveAgent(agent,null);

            return new ResponseEntity<>(purchaseInfo,HttpStatus.OK); 
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
    }
}
