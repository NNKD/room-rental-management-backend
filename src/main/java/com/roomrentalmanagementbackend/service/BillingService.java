package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;
import com.roomrentalmanagementbackend.dto.billing.response.ServiceDetailDTO;
import com.roomrentalmanagementbackend.entity.*;
import com.roomrentalmanagementbackend.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {

    @Autowired
    private RentalContractRepository rentalContractRepository;

    @Autowired
    private RentalContractBillRepository rentalContractBillRepository;

    @Autowired
    private ServiceBillRepository serviceBillRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UtilityServiceRepository utilityServiceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Payment createBillForApartment(int rentalContractId, List<ServiceDetailDTO> serviceDetails) {
        RentalContract rentalContract = rentalContractRepository.findById(rentalContractId)
                .orElseThrow(() -> new RuntimeException("Rental contract not found"));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String currentMonth = now.format(monthFormatter);

        RentalContractBill rentalBill = RentalContractBill.builder()
                .price(rentalContract.getPrice())
                .startDate(rentalContract.getStartDate().withMonth(1))
                .endDate(rentalContract.getEndDate().withMonth(1))
                .createdAt(now)
                .dueDate(now.plusDays(30))
                .rentalContract(rentalContract)
                .build();
        rentalContractBillRepository.save(rentalBill);

        Payment payment = Payment.builder()
                .name("Payment for " + rentalContract.getName() + " - Month " + currentMonth)
                .totalPrice(rentalBill.getPrice())
                .status("PENDING")
                .createdAt(now)
                .rentalContractBill(rentalBill)
                .user(rentalContract.getUser())
                .build();
        payment = paymentRepository.save(payment);
        entityManager.flush();

        Payment finalPayment = payment;
        List<ServiceBill> serviceBills = serviceDetails.stream().map(detail -> {
            UtilityService utilityService = utilityServiceRepository.findById(detail.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Utility service not found: " + detail.getServiceId()));
            ServiceBill serviceBill = ServiceBill.builder()
                    .rentalContract(rentalContract)
                    .utilityService(utilityService)
                    .name(utilityService.getName())
                    .quantity(detail.getQuantity())
                    .price(detail.getPrice())
                    .totalPrice(detail.getTotalPrice())
                    .createdAt(now)
                    .dueDate(now.plusDays(30))
                    .payment(finalPayment)
                    .build();
            return serviceBillRepository.save(serviceBill);
        }).collect(Collectors.toList());

        double totalServiceCost = serviceBills.stream().mapToDouble(ServiceBill::getTotalPrice).sum();
        payment.setTotalPrice(rentalBill.getPrice() + totalServiceCost);
        paymentRepository.save(payment);

        return payment;
    }

    @Transactional
    public Payment updateBill(int paymentId, BillResponseDTO bill) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        RentalContractBill rentalBill = payment.getRentalContractBill();
        rentalBill.setPrice(bill.getRentalAmount());
        rentalContractBillRepository.save(rentalBill);

        payment.setName(bill.getName());
        payment.setStatus(bill.getStatus());
        payment.setTotalPrice(bill.getTotalAmount());
        paymentRepository.save(payment);

        // Cập nhật ServiceBill
        List<ServiceBill> existingServiceBills = serviceBillRepository.findByPaymentId(paymentId);
        for (ServiceBill serviceBill : existingServiceBills) {
            serviceBillRepository.delete(serviceBill); // Xóa các ServiceBill cũ
        }
        List<ServiceBill> newServiceBills = bill.getServiceDetails().stream().map(detail -> {
            UtilityService utilityService = utilityServiceRepository.findById(detail.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Utility service not found: " + detail.getServiceId()));
            ServiceBill serviceBill = ServiceBill.builder()
                    .rentalContract(payment.getRentalContractBill().getRentalContract())
                    .utilityService(utilityService)
                    .name(utilityService.getName())
                    .quantity(detail.getQuantity())
                    .price(detail.getPrice())
                    .totalPrice(detail.getTotalPrice())
                    .createdAt(payment.getCreatedAt())
                    .dueDate(payment.getRentalContractBill().getDueDate())
                    .payment(payment)
                    .build();
            return serviceBillRepository.save(serviceBill);
        }).collect(Collectors.toList());

        return payment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}