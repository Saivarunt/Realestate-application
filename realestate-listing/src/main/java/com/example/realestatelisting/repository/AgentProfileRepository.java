package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.AgentProfile;

@Repository
public interface AgentProfileRepository extends JpaRepository<AgentProfile, Long> {

}
