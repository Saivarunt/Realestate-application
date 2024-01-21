package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.AgentAssociation;
import com.example.realestatelisting.models.AgentProfile;
import com.example.realestatelisting.models.User;

import java.util.List;

@Repository
public interface AgentAssociationRepository extends JpaRepository<AgentAssociation, Long> {
    @Query("SELECT aa FROM AgentAssociation aa WHERE aa.user_id = ?1 and aa.agent_Id = ?2")
    List<AgentAssociation> findByUser_idAndAgent_Id(User user_id, AgentProfile agent_Id);
}
