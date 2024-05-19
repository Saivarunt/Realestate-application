package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.UserPermissions;
import java.util.List;

@Repository
public interface UserPermissionsRepository extends JpaRepository<UserPermissions, Long>  {
    @Query("SELECT up FROM UserPermissions up WHERE user_id = ?1")
    List<UserPermissions> findByUser_id(User user_id);
}
