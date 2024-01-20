package com.example.realestatelisting.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agent-association")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgentAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "association_id")
    private Long associationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer")
    private User user_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent")
    private AgentProfile agent_Id;

    private Long price;
}
