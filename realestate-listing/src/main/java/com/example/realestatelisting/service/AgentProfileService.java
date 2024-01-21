package com.example.realestatelisting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.AgentProfileResponse;
import com.example.realestatelisting.models.dto.Rating;

@Service
public interface AgentProfileService {
    public AgentProfileResponse getAgentProfile(String agentId);
    public List<AgentProfileResponse> getAllProfiles();
    public AgentProfileResponse createAgent(User user);
    public AgentProfileResponse saveAgent(AgentProfile agent, Rating rating);
    public AgentProfile getAgentEntireProfile(String agentId);
    public AgentProfileResponse responseConverter(AgentProfile agent);
}
