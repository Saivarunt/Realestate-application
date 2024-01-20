package com.example.realestatelisting.models;

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
@Table(name = "property-details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyDetails {
    @Id
    @Column(name = "property_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long propertyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller")
    private User user_id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    private Long price;

    private Boolean availability =  true;

    private Integer rating = 0;

    private Long popularity = (long)0;
}
