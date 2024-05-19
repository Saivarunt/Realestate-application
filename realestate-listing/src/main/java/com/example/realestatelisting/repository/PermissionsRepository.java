package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.realestatelisting.models.Permissions;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    
}
