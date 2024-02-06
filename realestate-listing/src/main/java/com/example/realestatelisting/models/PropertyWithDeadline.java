package com.example.realestatelisting.models;

import java.util.Date;

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
@Table(name = "property-with-deadline")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyWithDeadline {

    @Id
    @Column(name = "deadline_property_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deadlinePropertyId;

    @OneToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private PropertyDetails property_id;

    private Date start_date;

    private Date end_date;
}
