package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.rentalContract.request.RentalContractRequest;
import com.roomrentalmanagementbackend.dto.rentalContract.response.RentalContractResponse;
import com.roomrentalmanagementbackend.service.RentalContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/rental-contracts")
@RequiredArgsConstructor
public class RentalContractController {
    private final RentalContractService rentalContractService;

    @GetMapping
    public ApiResponse<List<RentalContractResponse>> getAllRentalContracts() {
        try {
            List<RentalContractResponse> contracts = rentalContractService.getAllRentalContracts();
            return ApiResponse.success(contracts);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<RentalContractResponse>> getRentalContractsByUserId(@PathVariable int userId) {
        try {
            List<RentalContractResponse> contracts = rentalContractService.getRentalContractsByUserId(userId);
            return ApiResponse.success(contracts);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<RentalContractResponse> getRentalContractById(@PathVariable int id) {
        try {
            RentalContractResponse contract = rentalContractService.getRentalContractById(id);
            return ApiResponse.success(contract);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ApiResponse<RentalContractResponse> createRentalContract(@RequestBody RentalContractRequest request) {
        try {
            RentalContractResponse contract = rentalContractService.createRentalContract(request);
            return ApiResponse.success(contract);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<RentalContractResponse> updateRentalContract(@PathVariable int id, @RequestBody RentalContractRequest request) {
        try {
            RentalContractResponse contract = rentalContractService.updateRentalContract(id, request);
            return ApiResponse.success(contract);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRentalContract(@PathVariable int id) {
        try {
            rentalContractService.deleteRentalContract(id);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}