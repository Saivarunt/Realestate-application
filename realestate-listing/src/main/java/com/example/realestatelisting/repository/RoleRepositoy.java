package com.example.realestatelisting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.realestatelisting.models.Role;
import java.util.Optional;

@Repository
public interface RoleRepositoy  extends JpaRepository<Role,Long>{
    Optional<Role> findByAuthority(String authority);
}
