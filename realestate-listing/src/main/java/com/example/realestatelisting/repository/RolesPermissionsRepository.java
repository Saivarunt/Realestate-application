package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.realestatelisting.models.Role;
import com.example.realestatelisting.models.RolesPermissions;
import java.util.List;


public interface RolesPermissionsRepository extends JpaRepository<RolesPermissions, Long> {
    
    @Query("SELECT rp FROM RolesPermissions rp WHERE role_id = ?1")
    List<RolesPermissions> findByRole_id(Role role_id);
}
