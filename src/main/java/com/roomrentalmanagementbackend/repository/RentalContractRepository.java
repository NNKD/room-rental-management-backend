package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.RentalContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalContractRepository extends JpaRepository<RentalContract, Integer> {
    List<RentalContract> findByUserId(int userId);
}