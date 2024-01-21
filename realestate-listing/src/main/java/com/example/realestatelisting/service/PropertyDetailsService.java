package com.example.realestatelisting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.dto.PropertyDetailsResponse;
import com.example.realestatelisting.models.dto.PropertyInfoPost;
import com.example.realestatelisting.models.dto.Rating;
import com.example.realestatelisting.models.dto.UpdatePropertyInfo;

@Service
public interface PropertyDetailsService {
    public PropertyDetailsResponse saveProperty(PropertyInfoPost property);
    public PropertyDetailsResponse updateProperty(UpdatePropertyInfo property, String id);
    public List<PropertyDetailsResponse> getAllDetails();
    public PropertyDetailsResponse getDetailsById(String id);
    public List<PropertyDetailsResponse> getDetailsByName(String propertyname);
    public Boolean deletePropertyDetails(String id);
    public PropertyDetailsResponse save(PropertyDetails property);
    public Long averagePoplarity();
    public PropertyDetails getEntireDetailsById(String id);
    public PropertyDetailsResponse responseConverter(PropertyDetails property);
    public PropertyDetailsResponse savePropertyRating(PropertyDetails propertyDetails, Rating rating);
}
