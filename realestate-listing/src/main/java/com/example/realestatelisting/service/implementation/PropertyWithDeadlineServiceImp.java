package com.example.realestatelisting.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.Location;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.PropertyWithDeadline;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PropertyDetailsResponse;
import com.example.realestatelisting.models.dto.PropertyInfoPostWithDeadline;
import com.example.realestatelisting.repository.LocationRepository;
import com.example.realestatelisting.repository.PropertyDetailsRepository;
import com.example.realestatelisting.repository.PropertyWithDeadlineRepository;
import com.example.realestatelisting.repository.UserRepository;
import com.example.realestatelisting.service.PropertyWithDeadlineService;

@Service
public class PropertyWithDeadlineServiceImp implements PropertyWithDeadlineService{
    @Autowired
    PropertyWithDeadlineRepository propertyWithDeadlineRepository;

    @Autowired
    PropertyDetailsRepository propertyDetailsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserServiceImp userService;

    @Override
    public PropertyDetailsResponse saveProperty(PropertyInfoPostWithDeadline property) {
        User user = userRepository.findById(property.getUser_id()).get();
        Location propertyLocation = new Location(null, property.getLocation().getCountry(), property.getLocation().getCity(), property.getLocation().getState(), property.getLocation().getPrimary_address(), property.getLocation().getPincode(), property.getLocation().getCoordinates());

        Integer flag = 0;
        List<Location> locations = locationRepository.findAll();
        
        for(Location location : locations ){
        
            if(propertyLocation.getCoordinates().equals(location.getCoordinates())){
                flag = 1;
                propertyLocation = location;
            }
        
        }

        if(flag.equals(0)){
            locationRepository.save(propertyLocation);
        }

        PropertyDetails propertyDetails = new PropertyDetails(null, user, property.getName(), propertyLocation, property.getPrice(), true, 0, (long)0, -1, 0);
        PropertyDetails details = propertyDetailsRepository.save(propertyDetails);

        PropertyWithDeadline newPropertyWithDeadline = new PropertyWithDeadline(null,details,property.getStart_date(),property.getEnd_date());
        PropertyWithDeadline propertyWithDeadline = propertyWithDeadlineRepository.save(newPropertyWithDeadline);

        return new PropertyDetailsResponse(details.getPropertyId(), userService.responseConverter(user), details.getName(), propertyLocation, details.getPrice(), details.getAvailability(), details.getRating(), details.getPopularity());
    }

    @Override
    public List<PropertyWithDeadline> getAllDetails() {
        return propertyWithDeadlineRepository.findAll();
    }

    @Override
    public PropertyWithDeadline getDetailsById(String id) {
        return propertyWithDeadlineRepository.findById(Long.parseLong(id)).get();
    }

    @Override
    public PropertyWithDeadline getByProperty(PropertyDetails property) {
        return propertyWithDeadlineRepository.findByProperty(property).get(0);
    }

}
