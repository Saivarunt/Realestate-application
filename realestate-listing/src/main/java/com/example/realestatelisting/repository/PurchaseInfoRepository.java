package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.realestatelisting.models.PurchaseInfo;

public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfo,Long>{
    
}
