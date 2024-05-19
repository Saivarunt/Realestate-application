package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;

@Repository
public interface AgentProfileRepository extends JpaRepository<AgentProfile, Long> {

    @Query("SELECT ap FROM AgentProfile ap WHERE ap.userId = ?1")
    AgentProfile findByUserId(User userId);
}
