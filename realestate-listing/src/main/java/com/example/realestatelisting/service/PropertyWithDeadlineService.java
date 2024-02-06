package com.example.realestatelisting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.PropertyWithDeadline;
import com.example.realestatelisting.models.dto.PropertyDetailsResponse;
import com.example.realestatelisting.models.dto.PropertyInfoPostWithDeadline;

@Service
public interface PropertyWithDeadlineService {
    public PropertyDetailsResponse saveProperty(PropertyInfoPostWithDeadline property);
    public List<PropertyWithDeadline> getAllDetails();
    public PropertyWithDeadline getDetailsById(String id);
    public PropertyWithDeadline getByProperty(PropertyDetails property);
}
