package com.example.realestatelisting.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.AgentProfileResponse;
import com.example.realestatelisting.models.dto.Rating;
import com.example.realestatelisting.models.dto.UserInfoResponse;
import com.example.realestatelisting.repository.AgentProfileRepository;
import com.example.realestatelisting.service.AgentProfileService;

@Service
public class AgentProfileServiceImp  implements AgentProfileService {

    @Autowired
    AgentProfileRepository agentProfileRepository;

    @Autowired
    UserServiceImp userService;

    @Override
    public AgentProfileResponse getAgentProfile(String agentId) {
        AgentProfile requestedProfile = agentProfileRepository.findById(Long.parseLong(agentId)).get();
        UserInfoResponse user = userService.responseConverter(requestedProfile.getUserId());
        return new AgentProfileResponse(requestedProfile.getAgentId(), user, requestedProfile.getRating(), requestedProfile.getSale_count());
    }

    @Override
    public List<AgentProfileResponse> getAllProfiles() {

        List<AgentProfile> agents = agentProfileRepository.findAll();

        List<AgentProfileResponse> responses = new ArrayList<>();
        
        for(AgentProfile agent : agents){
            UserInfoResponse user = userService.responseConverter(agent.getUserId());
            responses.add(new AgentProfileResponse(agent.getAgentId(), user, agent.getRating(), agent.getSale_count()));
        }
        return responses;
    }


    @Override
    public Page<AgentProfileResponse> getAllProfilesByPage(Integer page) {

        Page<AgentProfile> agents = agentProfileRepository.findAll(PageRequest.of(page,10));

        List<AgentProfileResponse> responses = agents.getContent().stream()
        .map(details -> new AgentProfileResponse(
            details.getAgentId(), 
            userService.responseConverter(details.getUserId()), 
            details.getRating(), 
            details.getSale_count()
            ))
        .collect(Collectors.toList());
        
        return new PageImpl<>(responses, PageRequest.of(page, 10), agents.getTotalElements());
    }

    @Override
    public AgentProfileResponse createAgent(User user) {
        AgentProfile agent = agentProfileRepository.save(new AgentProfile((long) 0, user, 0, 0, -1, 0));
        UserInfoResponse userResponse = userService.responseConverter(agent.getUserId());
        return new AgentProfileResponse(agent.getAgentId(), userResponse, agent.getRating(), agent.getSale_count());
    }

    @Override
    public AgentProfileResponse saveAgent(AgentProfile agent, Rating rating) {

        if(rating != null){

            if(rating.getRating() > 5 || rating.getRating() < 0){
                throw new RuntimeException("Provide rating less than or equal to 5");
            }

            if(agent.getRated() == -1){
                agent.setRating_count(1);
                agent.setRating(rating.getRating());
                agent.setRated(rating.getRating());
            }
            else{
                agent.setRating_count(agent.getRating_count() + 1);
                agent.setRated(agent.getRated() + rating.getRating());
                agent.setRating(agent.getRated() / agent.getRating_count());
            }

        }

        AgentProfile profile = agentProfileRepository.save(agent);
        UserInfoResponse userResponse = userService.responseConverter(profile.getUserId());
        return new AgentProfileResponse(profile.getAgentId(), userResponse,profile.getRating(), profile.getSale_count());
    }

    @Override
    public AgentProfile getAgentEntireProfile(String agentId) {
        return agentProfileRepository.findById(Long.parseLong(agentId)).get();
    }

    @Override
    public AgentProfileResponse responseConverter(AgentProfile agent) {
        UserInfoResponse user = userService.responseConverter(agent.getUserId());
        return new AgentProfileResponse(agent.getAgentId(), user, agent.getRating(), agent.getSale_count());
    }

    @Override
    public AgentProfileResponse getByUserInfo(User userid) {
        AgentProfile agent = agentProfileRepository.findByUserId(userid);
        return responseConverter(agent);
    }
}
