package com.example.realestatelisting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    
    @GetMapping("/init")
    public ResponseEntity<String> adminLanding() {
        return new ResponseEntity<>("Welcome  Admin",HttpStatus.OK);
    }
}
