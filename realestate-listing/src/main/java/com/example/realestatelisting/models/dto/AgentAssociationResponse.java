package com.example.realestatelisting.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgentAssociationResponse {
    private Long associationId;

    private UserInfoResponse user_id;

    private AgentProfileResponse agent_Id;

    private Long price;
}
