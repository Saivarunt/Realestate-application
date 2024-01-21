package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.PurchaseInfo;

@Repository
public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfo, Long> {
    
}
