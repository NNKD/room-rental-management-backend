package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentManagementResponse;
import com.roomrentalmanagementbackend.service.ApartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardController {
    ApartmentService apartmentService;

    @GetMapping("/apartments")
    public ApiResponse<List<ApartmentManagementResponse>> getApartmentManagement() {
        return ApiResponse.success(apartmentService.getApartmentManagement());
    }
}
