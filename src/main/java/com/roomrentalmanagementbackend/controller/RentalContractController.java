package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.entity.RentalContract;
import com.roomrentalmanagementbackend.service.RentalContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rental-contracts")
@RequiredArgsConstructor
public class RentalContractController {
    private final RentalContractService rentalContractService;

    @GetMapping("/user/{userId}")
    public ApiResponse<List<RentalContract>> getRentalContractsByUserId(@PathVariable int userId) {
        List<RentalContract> contracts = rentalContractService.getRentalContractsByUserId(userId);
        return ApiResponse.success(contracts);
    }
}