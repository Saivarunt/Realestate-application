package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.PropertyDetails;
import com.example.realestatelisting.models.PropertyWithDeadline;
import java.util.List;


@Repository
public interface PropertyWithDeadlineRepository extends JpaRepository<PropertyWithDeadline, Long>{
    @Query("SELECT ppd FROM PropertyWithDeadline ppd WHERE ppd.property_id = ?1")
    List<PropertyWithDeadline> findByProperty(PropertyDetails property);
}
