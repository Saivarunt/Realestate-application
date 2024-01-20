package com.example.realestatelisting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.Location;

@Service
public interface LocationService {
    List<Location> getAllLocations();
    Location getLocationById(String id);
    List<Location> getLocationByCity(String city);
    List<Location> getLocationByState(String state);
    List<Location> getLocationByCountry(String country);
}
