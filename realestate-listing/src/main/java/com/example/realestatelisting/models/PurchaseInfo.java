package com.example.realestatelisting.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase-info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseInfo {
    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer")
    private User user_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent")
    @Nullable
    private AgentProfile agent_Id;

    @OneToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private PropertyDetails property;
}
