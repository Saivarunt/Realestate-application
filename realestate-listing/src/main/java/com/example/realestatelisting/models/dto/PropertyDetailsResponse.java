package com.example.realestatelisting.models.dto;

import com.example.realestatelisting.models.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PropertyDetailsResponse {
    private Long propertyId;

    private UserInfoResponse user_id;

    private String name;

    private Location location;

    private Long price;

    private Boolean availability;

    private Integer rating;

    private Long popularity;
}
