package com.example.realestatelisting.models.dto;

import java.util.Date;

import com.example.realestatelisting.models.Location;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyInfoPostWithDeadline {
    private Long user_id;

    private String name;

    private Location location;

    private Long price;

    private Date start_date;

    private Date end_date;
}
