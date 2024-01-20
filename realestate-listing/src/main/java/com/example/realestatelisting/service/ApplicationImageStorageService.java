package com.example.realestatelisting.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestatelisting.models.ApplicationImageStorage;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.User;

@Service
public interface ApplicationImageStorageService {
    public String uploadImage(MultipartFile file, String type, String primaryField);
    public ApplicationImageStorage findImage(User user);
    public byte[] getByFileName(String path);
    public Boolean deleteImage(ApplicationImageStorage image);
    public ApplicationImageStorage findPropertyImage(PropertyDetails property);
}
