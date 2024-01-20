package com.example.realestatelisting.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.PurchaseInfo;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PurchaseInfoResponse;
import com.example.realestatelisting.repository.PurchaseInfoRepository;
import com.example.realestatelisting.service.PurchaseInfoService;

@Service
public class PurchaseInfoServiceImp  implements PurchaseInfoService{
    @Autowired
    PurchaseInfoRepository purchaseInfoRepository;

    @Autowired
    PropertyDetailsServiceImp propertyDetailsService;

    @Autowired
    UserServiceImp userService;

    @Autowired
    AgentProfileServiceImp agentProfileService;

    @Override
    public PurchaseInfoResponse savePurchaseInfo(User user, AgentProfile agent, PropertyDetails propertyDetails) {
        PurchaseInfo purchase = new PurchaseInfo(null, user, agent, propertyDetails);
        purchaseInfoRepository.save(purchase);

        return new PurchaseInfoResponse(purchase.getPurchaseId(), userService.responseConverter(user), agentProfileService.responseConverter(agent), propertyDetailsService.responseConverter(propertyDetails));
    }

    
}
