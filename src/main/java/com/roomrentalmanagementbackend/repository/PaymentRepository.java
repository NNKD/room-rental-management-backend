package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}