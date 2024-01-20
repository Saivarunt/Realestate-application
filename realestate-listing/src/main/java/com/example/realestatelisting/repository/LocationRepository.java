package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.realestatelisting.models.Location;
import java.util.List;


public interface LocationRepository extends JpaRepository<Location,Long>{
    List<Location> findByCity(String city);
    List<Location> findByCountry(String country);
    List<Location> findByState(String state);
}
