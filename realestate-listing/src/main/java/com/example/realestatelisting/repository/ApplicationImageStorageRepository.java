package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.realestatelisting.models.ApplicationImageStorage;
import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.User;



public interface ApplicationImageStorageRepository extends JpaRepository<ApplicationImageStorage,Long>{
    @Query("SELECT ai FROM ApplicationImageStorage ai WHERE ai.user_id = ?1 ")
    ApplicationImageStorage findByUser_id(User user_id);

    @Query("SELECT ai FROM ApplicationImageStorage ai WHERE ai.property_id = ?1 ")
    ApplicationImageStorage findByProperty_id(PropertyDetails property_id);

}
