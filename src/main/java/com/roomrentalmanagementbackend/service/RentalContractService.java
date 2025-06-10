package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.entity.RentalContract;
import com.roomrentalmanagementbackend.repository.RentalContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalContractService {
    private final RentalContractRepository rentalContractRepository;

    @Transactional(readOnly = true)
    public List<RentalContract> getRentalContractsByUserId(int userId) {
        return rentalContractRepository.findByUserId(userId);
    }
}