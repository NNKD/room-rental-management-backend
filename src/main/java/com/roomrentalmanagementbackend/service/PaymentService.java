package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;
import com.roomrentalmanagementbackend.dto.billing.response.ServiceDetailDTO;
import com.roomrentalmanagementbackend.entity.*;
import com.roomrentalmanagementbackend.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PaymentService {
    PaymentRepository paymentRepository;

    public void updatePaymentStatus(int id) {
        Payment p = paymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Null"));
        p.setStatus("PAID");
        paymentRepository.save(p);
    }

}