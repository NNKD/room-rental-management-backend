package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.ApartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentTypeRepository extends JpaRepository<ApartmentType, Integer> {
    List<ApartmentType> findAll();
}
