package com.example.realestatelisting.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.Location;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PropertyDetailsResponse;
import com.example.realestatelisting.models.dto.PropertyInfoPost;
import com.example.realestatelisting.models.dto.Rating;
import com.example.realestatelisting.models.dto.UpdatePropertyInfo;
import com.example.realestatelisting.repository.LocationRepository;
import com.example.realestatelisting.repository.PropertyDetailsRepository;
import com.example.realestatelisting.repository.UserRepository;
import com.example.realestatelisting.service.PropertyDetailsService;

@Service
public class PropertyDetailsServiceImp implements PropertyDetailsService {

    @Autowired
    PropertyDetailsRepository propertyDetailsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserServiceImp userService;

    @Override
    public Long averagePoplarity() {
        List<PropertyDetails> properties = propertyDetailsRepository.findAll();
        Long totalPopularity = (long)0;
        
        for(PropertyDetails property : properties){
            
            if(property.getAvailability()) {
                totalPopularity += property.getPopularity();
            }
        
        }

        return totalPopularity / properties.size();
    }

    @Override
    public PropertyDetailsResponse saveProperty(PropertyInfoPost property) {
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
        return new PropertyDetailsResponse(details.getPropertyId(), userService.responseConverter(user), details.getName(), propertyLocation, details.getPrice(), details.getAvailability(), details.getRating(), details.getPopularity());
    }

    @Override
    public PropertyDetailsResponse updateProperty(UpdatePropertyInfo property, String id) {
        PropertyDetails propertyInformation = propertyDetailsRepository.findById(Long.parseLong(id)).get();

        User user = userRepository.findById(property.getUser_id()).get();
        Location location = locationRepository.findById(propertyInformation.getLocation().getLocationId()).get();
        location.setCity(property.getLocation().getCity());
        location.setState(property.getLocation().getState());
        location.setCountry(property.getLocation().getCountry());
        location.setPrimary_address(property.getLocation().getPrimary_address());
        location.setPincode(property.getLocation().getPincode());
        location.setCoordinates(property.getLocation().getCoordinates());

        locationRepository.save(location);

        propertyInformation.setUser_id(user);
        propertyInformation.setName(property.getName());
        propertyInformation.setLocation(location);
        propertyInformation.setPrice(property.getPrice()); 
        propertyInformation.setAvailability(property.getAvailability());
        propertyInformation.setRating(property.getRating());
        propertyInformation.setPopularity(property.getPopularity());


        PropertyDetails details = propertyDetailsRepository.save(propertyInformation);
        return new PropertyDetailsResponse(details.getPropertyId(), userService.responseConverter(user), details.getName(), location, details.getPrice(), details.getAvailability(), details.getRating(), details.getPopularity());
    }

    @Override
    public List<PropertyDetailsResponse> getAllDetails() {

        List<PropertyDetails> properties = propertyDetailsRepository.findAll();

        List<PropertyDetailsResponse> responses = new ArrayList<>();

        for(PropertyDetails details : properties){
            responses.add(new PropertyDetailsResponse(details.getPropertyId(), userService.responseConverter(details.getUser_id()), details.getName(), details.getLocation(), details.getPrice(), details.getAvailability(), details.getRating(), details.getPopularity()));
        }

        return responses;
    }

    @Override
    public PropertyDetailsResponse getDetailsById(String id) {
        PropertyDetails property = propertyDetailsRepository.findById(Long.parseLong(id)).get();
        
        if(property.getPopularity() > averagePoplarity() && property.getAvailability()) {
            property.setPrice(property.getPrice() + 99);
        }

        property.setPopularity(property.getPopularity() + 1);
        propertyDetailsRepository.save(property);
        
        return new PropertyDetailsResponse(property.getPropertyId(), userService.responseConverter(property.getUser_id()), property.getName(), property.getLocation(), property.getPrice(), property.getAvailability(), property.getRating(), property.getPopularity());
    }

    @Override
    public List<PropertyDetailsResponse> getDetailsByName(String propertyname) {
        List<PropertyDetails> properties = propertyDetailsRepository.findByName(propertyname);
        List<PropertyDetailsResponse> responses = new ArrayList<>();
        Long averagePopularity = averagePoplarity();

        for(PropertyDetails property : properties){

            if(property.getPopularity() > averagePopularity && property.getAvailability()) {
                property.setPrice(property.getPrice() + 99);
            }
            
            property.setPopularity(property.getPopularity() + 1);
            propertyDetailsRepository.save(property);
            responses.add(new PropertyDetailsResponse(property.getPropertyId(), userService.responseConverter(property.getUser_id()), property.getName(), property.getLocation(), property.getPrice(), property.getAvailability(), property.getRating(), property.getPopularity()));

        }

        return responses;
    }

    @Override
    public Boolean deletePropertyDetails(String id) {
        propertyDetailsRepository.deleteById(Long.parseLong(id));
        return true;
    }

    @Override
    public PropertyDetailsResponse save(PropertyDetails property) {
        PropertyDetails details = propertyDetailsRepository.save(property);
        return new PropertyDetailsResponse(details.getPropertyId(), userService.responseConverter(property.getUser_id()), details.getName(), property.getLocation(), details.getPrice(), details.getAvailability(), details.getRating(), details.getPopularity());
    }

    @Override
    public PropertyDetails getEntireDetailsById(String id) {
        PropertyDetails property = propertyDetailsRepository.findById(Long.parseLong(id)).get();
        
        if(property.getPopularity() > averagePoplarity() && property.getAvailability()) {
            property.setPrice(property.getPrice() + 99);
        }

        property.setPopularity(property.getPopularity() + 1);
        propertyDetailsRepository.save(property);
        
        return property;
    }

    @Override
    public PropertyDetailsResponse responseConverter(PropertyDetails property) {
        return new PropertyDetailsResponse(property.getPropertyId(), userService.responseConverter(property.getUser_id()), property.getName(), property.getLocation(), property.getPrice(), property.getAvailability(), property.getRating(), property.getPopularity());
    }

    @Override
    public PropertyDetailsResponse savePropertyRating(PropertyDetails propertyDetails, Rating rating) {

        if(rating != null){

            if(rating.getRating() > 5 || rating.getRating() < 0){
                throw new RuntimeException("Provide rating equal to 0 or less than or equal to 5");
            }
                
            if(propertyDetails.getRated() == -1){
                propertyDetails.setRating_count(1);
                propertyDetails.setRating(rating.getRating());
                propertyDetails.setRated(rating.getRating());
            }
            else{
                propertyDetails.setRated(propertyDetails.getRated() + rating.getRating());
                propertyDetails.setRating_count(propertyDetails.getRating_count() + 1);
                propertyDetails.setRating(propertyDetails.getRated()  / propertyDetails.getRating_count());
            }

        }

        PropertyDetails details = propertyDetailsRepository.save(propertyDetails);
        return new PropertyDetailsResponse(details.getPropertyId(), userService.responseConverter(details.getUser_id()), details.getName(), details.getLocation(), details.getPrice(), details.getAvailability(), details.getRating(), details.getPopularity());
    
    }

}
