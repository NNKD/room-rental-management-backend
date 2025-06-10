package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.apartment.UtilityServiceDTO;
import com.roomrentalmanagementbackend.entity.UtilityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilityServiceRepository extends JpaRepository<UtilityService, Integer> {
    List<UtilityService> findAll();
    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                UtilityServiceDTO(
                    u.id,
                    u.name,
                    u.description,
                    u.price,
                    u.unit
                )
            FROM UtilityService u
            """)
    List<UtilityServiceDTO> findAllService();
}
