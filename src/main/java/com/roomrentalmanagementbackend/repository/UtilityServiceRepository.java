package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.UtilityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilityServiceRepository extends JpaRepository<UtilityService, Integer> {
    List<UtilityService> findAll();
}
