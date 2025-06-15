package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.billing.request.CreateBillRequest;
import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;
import com.roomrentalmanagementbackend.dto.billing.response.ServiceDetailDTO;
import com.roomrentalmanagementbackend.entity.Payment;
import com.roomrentalmanagementbackend.entity.RentalContractBill;
import com.roomrentalmanagementbackend.repository.PaymentRepository;
import com.roomrentalmanagementbackend.repository.ServiceBillRepository;
import com.roomrentalmanagementbackend.service.BillingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard/billing")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillingController {

     BillingService billingService;
    ServiceBillRepository serviceBillRepository;
     PaymentRepository paymentRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private BillResponseDTO mapPaymentToResponse(Payment payment) {
        RentalContractBill rentalBill = payment.getRentalContractBill();
        BillResponseDTO response = new BillResponseDTO();
        response.setId(payment.getId());
        response.setName(payment.getName());
        response.setRentalAmount(rentalBill.getPrice());
        response.setServiceDetails(serviceBillRepository.findByPaymentId(payment.getId()).stream().map(serviceBill -> {
            ServiceDetailDTO dto = new ServiceDetailDTO();
            dto.setName(serviceBill.getName());
            dto.setQuantity(serviceBill.getQuantity());
            dto.setPrice(serviceBill.getPrice());
            dto.setTotalPrice(serviceBill.getTotalPrice());
            dto.setRentalContractId(serviceBill.getRentalContract().getId());
            dto.setServiceId(serviceBill.getUtilityService().getId());
            return dto;
        }).collect(Collectors.toList()));
        response.setTotalAmount(payment.getTotalPrice());
        response.setCreatedAt(rentalBill.getCreatedAt());
        response.setDueDate(rentalBill.getDueDate());
        response.setStatus(payment.getStatus());
        return response;
    }

    @GetMapping
    public List<BillResponseDTO> getAllBills() {
        List<Payment> payments = billingService.getAllPayments();
        return payments.stream()
                .map(this::mapPaymentToResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/create/{rentalContractId}")
    public BillResponseDTO createBill(@PathVariable int rentalContractId, @RequestBody CreateBillRequest request) {
        Payment payment = billingService.createBillForApartment(rentalContractId, request.getServiceDetails());
        return mapPaymentToResponse(payment);
    }

    @PutMapping("/{id}")
    public BillResponseDTO updateBill(@PathVariable int id, @RequestBody BillResponseDTO bill) {
        Payment updatedPayment = billingService.updateBill(id, bill);
        return mapPaymentToResponse(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable int id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }
}