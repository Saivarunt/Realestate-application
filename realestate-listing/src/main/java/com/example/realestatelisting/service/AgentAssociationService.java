package com.example.realestatelisting.service;

import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.AgentAssociationResponse;

@Service
public interface AgentAssociationService {
    public AgentAssociationResponse saveAssociation(User user, AgentProfile agent, String price);
    public AgentProfile findAssociation(User user, String agentId);
}
