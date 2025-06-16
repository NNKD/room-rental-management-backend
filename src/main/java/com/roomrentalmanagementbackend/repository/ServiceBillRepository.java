package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.ServiceBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceBillRepository extends JpaRepository<ServiceBill, Integer> {
    List<ServiceBill> findByRentalContractId(int rentalContractId);
    List<ServiceBill> findByPaymentId(int paymentId); // Thêm phương thức này
}