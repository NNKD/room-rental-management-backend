package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.RentalContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalContractRepository extends JpaRepository<RentalContract, Integer> {
    @Query("SELECT rc FROM RentalContract rc WHERE rc.user.id = :userId AND rc.status = 'ACTIVE'")
    List<RentalContract> findByUserId(@Param("userId") Integer userId);
}