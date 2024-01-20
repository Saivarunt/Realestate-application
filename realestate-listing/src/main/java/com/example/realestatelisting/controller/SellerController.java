package com.example.realestatelisting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestatelisting.models.ApplicationImageStorage;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PropertyDetailsResponse;
import com.example.realestatelisting.models.dto.PropertyInfoPost;
import com.example.realestatelisting.models.dto.UpdatePropertyInfo;
import com.example.realestatelisting.service.implementation.ApplicationImageStorageServiceImp;
import com.example.realestatelisting.service.implementation.PropertyDetailsServiceImp;
import com.example.realestatelisting.service.implementation.TokenService;
import com.example.realestatelisting.service.implementation.UserServiceImp;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/seller")
@CrossOrigin("*")
public class SellerController {
    
    @Autowired
    PropertyDetailsServiceImp propertyDetailsService;
 
    @Autowired
    TokenService tokenService;

    @Autowired
    ApplicationImageStorageServiceImp applicationImageStorageService;

    @Autowired
    UserServiceImp userService;

    @PostMapping("/post-property-info")
    public ResponseEntity<PropertyDetailsResponse> postPropertyInfo(@RequestBody PropertyInfoPost body,  HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getEntireUser(username);
        
        if(body.getUser_id().equals(user.getUserId())){
            return new ResponseEntity<>(propertyDetailsService.saveProperty(body), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    
    @PutMapping("/update-property-info/{id}")
    public ResponseEntity<PropertyDetailsResponse> updatePropertyInfo(@RequestBody UpdatePropertyInfo body, @PathVariable String id , HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getEntireUser(username);
        
        if(body.getUser_id().equals(user.getUserId())){
            return new ResponseEntity<>(propertyDetailsService.updateProperty(body,id), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete-property-info/{id}")
    public ResponseEntity<Boolean> deletePropertyInfo(@PathVariable String id, HttpServletRequest request){
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);

        PropertyDetails propertyDetails = propertyDetailsService.getEntireDetailsById(id);

        if(propertyDetails.getUser_id().getUsername().equals(username)){
            return new ResponseEntity<>(propertyDetailsService.deletePropertyDetails(id),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/image-upload-to-property/{id}")
    public ResponseEntity<String> addImage(@PathVariable String id, @RequestParam("image")MultipartFile file , HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        PropertyDetails property = propertyDetailsService.getEntireDetailsById(id);

        if (property.getUser_id().getUsername().equals(username)){
            return new ResponseEntity<>(applicationImageStorageService.uploadImage(file, "property", id),HttpStatus.OK);
        }

        return new ResponseEntity<>("",HttpStatus.FORBIDDEN);

    }
    
    @DeleteMapping("/delete-property-image/{id}/{fileName}")
    public ResponseEntity<Boolean> deleteImageToFacilitySystem(@PathVariable String id,@PathVariable String fileName, HttpServletRequest request) {

        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        PropertyDetails property = propertyDetailsService.getEntireDetailsById(id);
        ApplicationImageStorage image = applicationImageStorageService.findPropertyImage(property);

        if(property.getUser_id().getUsername().equals(username)){
            return new ResponseEntity<>(applicationImageStorageService.deleteImage(image), HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);

	}
}
