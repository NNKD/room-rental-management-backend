package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.Apartment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    Page<Apartment> findAll(Pageable pageable);
}
