package com.example.realestatelisting.service.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestatelisting.models.ApplicationImageStorage;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.repository.ApplicationImageStorageRepository;
import com.example.realestatelisting.repository.PropertyDetailsRepository;
import com.example.realestatelisting.repository.UserRepository;
import com.example.realestatelisting.service.ApplicationImageStorageService;

@Service
public class ApplicationImageStorageServiceImp implements ApplicationImageStorageService {

    @Autowired
    ApplicationImageStorageRepository applicationImageStorageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PropertyDetailsRepository propertyDetailsRepository;

    @Override
    public String uploadImage(MultipartFile file, String type, String primaryField) {
        String filePath = "http://localhost:8080/static/images/" + file.getOriginalFilename();

        if(file.getContentType().split("/")[0].equals("image")){

            try {
                file.transferTo(new File(filePath));
            }
            catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return "Error :" + e;
            }
            
            if(type == "user"){
                User user = userRepository.findById(Long.parseLong(primaryField)).get();
                ApplicationImageStorage image = new ApplicationImageStorage(null, null, user, filePath);
                applicationImageStorageRepository.save(image);
            }
            else if(type == "property"){
                PropertyDetails property = propertyDetailsRepository.findById(Long.parseLong(primaryField)).get();
                ApplicationImageStorage image = new ApplicationImageStorage(null, property, null, filePath);
                applicationImageStorageRepository.save(image);
            }

            return "Uploaded Successfully";
        }
        else{
            return "Not Allowed";
        }

    }

    @Override
    public ApplicationImageStorage findImage(User user) {
        return applicationImageStorageRepository.findByUser_id(user);
    }
    

    @Override	
    public byte[] getByFileName(String path){
        byte[] images;
		try {
			images = Files.readAllBytes(new File(path).toPath());
			return images;
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[1];
		}
	}

    @Override
    public Boolean deleteImage(ApplicationImageStorage image) {
        applicationImageStorageRepository.deleteById(image.getImageId());
		File target = new File(image.getImage_url());
		return target.delete();
	}

    @Override
    public ApplicationImageStorage findPropertyImage(PropertyDetails property) {
        return applicationImageStorageRepository.findByProperty_id(property);
    }
}
