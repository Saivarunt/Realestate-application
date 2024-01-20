package com.example.realestatelisting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestatelisting.models.ApplicationImageStorage;
import com.example.realestatelisting.models.Location;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.dto.PropertyDetailsResponse;
import com.example.realestatelisting.models.dto.UpdateUserInfo;
import com.example.realestatelisting.models.dto.UserInfoResponse;
import com.example.realestatelisting.service.implementation.ApplicationImageStorageServiceImp;
import com.example.realestatelisting.service.implementation.LocationServiceImp;
import com.example.realestatelisting.service.implementation.PropertyDetailsServiceImp;
import com.example.realestatelisting.service.implementation.TokenService;
import com.example.realestatelisting.service.implementation.UserServiceImp;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    
    @Autowired
    UserServiceImp userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PropertyDetailsServiceImp propertyDetailsService;

    @Autowired
    ApplicationImageStorageServiceImp applicationImageStorageService;

    @Autowired
    LocationServiceImp locationService;

    @GetMapping("/init")
    public ResponseEntity<String> userLanding() {
        return new ResponseEntity<>("Welcome",HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/by-name/{username}")
    public ResponseEntity<UserInfoResponse> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUser(username),HttpStatus.OK);
    }

    @PostMapping("/user-info")
    public ResponseEntity<UserInfoResponse> postMethodName(@RequestBody UpdateUserInfo body, HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);

        if(body.getUsername().equals(username)){
            User user = userService.getEntireUser(body.getUsername());
            user.setFullName(body.getFullname());
            user.setEmail(body.getEmail());
            user.setPhoneNumber(body.getPhonenumber());
            return new ResponseEntity<>(userService.saveUser(user),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDetailsResponse>> getAllPropertyDetails() {
        return new ResponseEntity<>(propertyDetailsService.getAllDetails(),HttpStatus.OK);
    }
    
    @GetMapping("/property-id/{id}")
    public ResponseEntity<PropertyDetailsResponse> getByPropertyId(@PathVariable String id) {
        return new ResponseEntity<>(propertyDetailsService.getDetailsById(id),HttpStatus.OK);
    }

    @GetMapping("/property-name/{propertyname}")
    public ResponseEntity<List<PropertyDetailsResponse>> getByPropertyName(@PathVariable String propertyname) {
        return new ResponseEntity<>(propertyDetailsService.getDetailsByName(propertyname),HttpStatus.OK);
    }

    @PostMapping("/image-upload-to-user/{id}")
    public ResponseEntity<String> addImage(@PathVariable String id, @RequestParam("image")MultipartFile file, HttpServletRequest request) {
        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getEntireUser(username);
        System.out.println(Long.parseLong(id) == (long) user.getUserId());

        if(Long.parseLong(id) == (long) user.getUserId()){
            return new ResponseEntity<>(applicationImageStorageService.uploadImage(file, "user", id),HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/with-image/{id}/{fileName}")
	public ResponseEntity<byte[]> downloadImageFromFacilitySystem(@PathVariable String id, @PathVariable String fileName,HttpServletRequest request){

        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getEntireUser(username);
        ApplicationImageStorage image = applicationImageStorageService.findImage(user);

        if (image.getImage_url().equals("D:\\varun\\college\\trustrace\\code\\backend-task\\realestate-listing\\images\\" + fileName) && Long.parseLong(id) == (long) user.getUserId()){			
            byte[] imageData=applicationImageStorageService.getByFileName("D:\\varun\\college\\trustrace\\code\\backend-task\\realestate-listing\\images\\"+fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpg"))
                    .body(imageData);
        }

        return new ResponseEntity<byte[]>(new byte[1], HttpStatus.FORBIDDEN);

	}
    
    @DeleteMapping("/delete/{id}/{fileName}")
    public ResponseEntity<Boolean> deleteImageToFacilitySystem(@PathVariable String id,@PathVariable String fileName, HttpServletRequest request) {

        String username = tokenService.validateJWTForUserInfo(request.getHeader("Authorization").split(" ",2)[1]);
        User user = userService.getEntireUser(username);
        ApplicationImageStorage image = applicationImageStorageService.findImage(user);

        if(Long.parseLong(id) == (long) user.getUserId()){
            Boolean deletedImage = applicationImageStorageService.deleteImage(image);
            if (deletedImage){
                return ResponseEntity.status(HttpStatus.OK)
                    .body(deletedImage);
            }
        }

        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> findAllLocation() {
        return new ResponseEntity<>(locationService.getAllLocations(),HttpStatus.OK);
    }
    
    @GetMapping("/locations/{id}")
    public ResponseEntity<Location> findLocationById(@PathVariable String id) {
        return new ResponseEntity<>(locationService.getLocationById(id),HttpStatus.OK);
    }

    @GetMapping("/locations/{city}")
    public ResponseEntity<List<Location>> findLocationByCity(@PathVariable String city) {
        return new ResponseEntity<>(locationService.getLocationByCity(city),HttpStatus.OK);
    }

    @GetMapping("/locations/{state}")
    public ResponseEntity<List<Location>> findLocationByState(@PathVariable String state) {
        return new ResponseEntity<>(locationService.getLocationByState(state),HttpStatus.OK);
    }

    @GetMapping("/locations/{country}")
    public ResponseEntity<List<Location>> findLocationBycountry(@PathVariable String country) {
        return new ResponseEntity<>(locationService.getLocationByCountry(country),HttpStatus.OK);
    }
}