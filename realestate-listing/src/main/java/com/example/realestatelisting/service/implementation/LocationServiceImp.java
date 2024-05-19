package com.example.realestatelisting.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.Location;
import com.example.realestatelisting.repository.LocationRepository;
import com.example.realestatelisting.service.LocationService;

@Service
public class LocationServiceImp implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> getAllLocationsPage(Integer page) {
        return locationRepository.findAll(PageRequest.of(page,10));
    }


    @Override
    public Location getLocationById(String id) {
        return locationRepository.findById(Long.parseLong(id)).get();
    }

    @Override
    public List<Location> getLocationByCity(String city) {
        return locationRepository.findByCity(city);
    }

    @Override
    public List<Location> getLocationByState(String state) {
        return locationRepository.findByState(state);
    }

    @Override
    public List<Location> getLocationByCountry(String country) {
        return locationRepository.findByCountry(country);
    }
    
}
