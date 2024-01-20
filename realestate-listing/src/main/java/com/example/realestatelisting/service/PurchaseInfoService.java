package com.example.realestatelisting.service;

import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PurchaseInfoResponse;

@Service
public interface PurchaseInfoService {
    public PurchaseInfoResponse savePurchaseInfo(User user, AgentProfile agent, PropertyDetails propertyDetails);
}
