package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.RentalContract;
import com.roomrentalmanagementbackend.entity.RentalContractBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalContractBillRepository extends JpaRepository<RentalContractBill, Integer> {
    @Query("SELECT rcb FROM RentalContractBill rcb WHERE rcb.rentalContract IN :contracts")
    List<RentalContractBill> findByRentalContractIn(@Param("contracts") List<RentalContract> contracts);
}