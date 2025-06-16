package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.billing.request.CreateBillRequest;
import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;
import com.roomrentalmanagementbackend.dto.billing.response.ServiceDetailDTO;
import com.roomrentalmanagementbackend.entity.Payment;
import com.roomrentalmanagementbackend.entity.RentalContractBill;
import com.roomrentalmanagementbackend.repository.PaymentRepository;
import com.roomrentalmanagementbackend.repository.ServiceBillRepository;
import com.roomrentalmanagementbackend.repository.UserRepository;
import com.roomrentalmanagementbackend.service.BillingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
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
    UserRepository userRepository;

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
    public ApiResponse<List<BillResponseDTO>> getAllBills() {
        try {
            List<Payment> payments = billingService.getAllPayments();
            List<BillResponseDTO> billResponses = payments.stream()
                    .map(this::mapPaymentToResponse)
                    .collect(Collectors.toList());
            return ApiResponse.success(billResponses);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "serverError");
        }
    }

    @PostMapping("/create/{rentalContractId}")
    public ApiResponse<BillResponseDTO> createBill(
            @PathVariable int rentalContractId,
            @RequestBody CreateBillRequest request) {
        try {
            Payment payment = billingService.createBillForApartment(rentalContractId, request.getServiceDetails());
            return ApiResponse.success(mapPaymentToResponse(payment));
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "serverError");
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<BillResponseDTO> updateBill(
            @PathVariable int id,
            @RequestBody BillResponseDTO bill) {
        try {
            Payment updatedPayment = billingService.updateBill(id, bill);
            return ApiResponse.success(mapPaymentToResponse(updatedPayment));
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "serverError");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBill(@PathVariable int id) {
        try {
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("paymentNotFound"));
            paymentRepository.delete(payment);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "serverError");
        }
    }
}