package com.example.realestatelisting.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.UserPermissions;
import com.example.realestatelisting.repository.UserPermissionsRepository;
import com.example.realestatelisting.repository.UserRepository;

@Service
public class PermissionsService {
    @Autowired
    UserPermissionsRepository userPermisionsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepository userRepo;
    
    public boolean hasAccess(String id,String permission){

        User user = userRepository.findById(Long.parseLong(id)).get();
        List<UserPermissions> userPermisions = userPermisionsRepository.findByUser_id(user);
        System.out.println("-----------------------------");
        System.out.println("Inside Permissions Service");
        System.out.println("-----------------------------");
        if(userPermisions.size() == 0){
            return false;
        }

        Boolean canAccess = userPermisions.stream().anyMatch(val -> val.getPermission_id().getPermission().equalsIgnoreCase(permission));
        System.out.println("canAccess"+canAccess);
        return canAccess;
    }
}
