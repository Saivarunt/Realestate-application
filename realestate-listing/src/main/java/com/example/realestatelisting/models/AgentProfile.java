package com.example.realestatelisting.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agent-profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgentProfile {
    @Id
    @Column(name = "agent_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long agentId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;

    private Integer rating = 0;

    private Integer sale_count = 0;

    private Integer rated = 0;
    private Integer rating_count = 0;
}
