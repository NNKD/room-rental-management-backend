package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.Payment;
import com.roomrentalmanagementbackend.entity.RentalContractBill;
import com.roomrentalmanagementbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.rentalContractBill IN :rentalBills ORDER BY p.createdAt DESC")
    List<Payment> findByRentalContractBillIn(@Param("rentalBills") List<RentalContractBill> rentalBills);

    @Query("SELECT p FROM Payment p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Payment> findByUserId(@Param("userId") Integer userId);

    List<Payment> findByUser(User user);

    @Override
    Optional<Payment> findById(Integer integer);
}