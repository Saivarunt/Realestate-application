package com.example.realestatelisting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgentProfileResponse {
    private Long agentId;

    private UserInfoResponse userId;

    private Integer rating;

    private Integer sale_count;
}
