package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.PropertyDetails;
import java.util.List;

@Repository
public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails, Long> {
    List<PropertyDetails> findByName(String name);

    @Query("SELECT p FROM PropertyDetails p WHERE name LIKE %?1%")
    List<PropertyDetails> findByPropertyName(String name);
    
}
