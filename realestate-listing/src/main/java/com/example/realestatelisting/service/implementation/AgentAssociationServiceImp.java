package com.example.realestatelisting.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.AgentAssociation;
import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.AgentAssociationResponse;
import com.example.realestatelisting.repository.AgentAssociationRepository;
import com.example.realestatelisting.repository.AgentProfileRepository;
import com.example.realestatelisting.service.AgentAssociationService;

import jakarta.persistence.NoResultException;

@Service
public class AgentAssociationServiceImp implements AgentAssociationService {

    @Autowired
    AgentAssociationRepository agentAssociationRepository;

    @Autowired
    AgentProfileRepository agentProfileRepository;
    
    @Autowired
    UserServiceImp userService;

    @Autowired
    AgentProfileServiceImp agentProfileService;

    @Override
    public AgentAssociationResponse saveAssociation(User user, AgentProfile agent, String price) {
        AgentAssociation association =  agentAssociationRepository.save(new AgentAssociation(null, user, agent, Long.parseLong(price)));
        AgentAssociationResponse associationResponse = new AgentAssociationResponse(association.getAssociationId(), userService.responseConverter(user), agentProfileService.responseConverter(agent), association.getPrice());
        return associationResponse;
    }

    @Override
    public AgentProfile findAssociation(User user, String agentId) {
        AgentProfile agent = agentProfileRepository.findById(Long.parseLong(agentId)).orElse(null); 

        if(agent == null){
            return null;
        }
        else if (!agentAssociationRepository.findByUser_idAndAgent_Id(user, agent).isEmpty()) { 
            return agent;
        }
        else{
            throw new NoResultException("No such association found");
        }
        
    }

}
