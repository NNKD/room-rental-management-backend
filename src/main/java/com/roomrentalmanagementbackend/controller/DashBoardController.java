package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentStatusDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentTypeDTO;
import com.roomrentalmanagementbackend.dto.apartment.UtilityServiceDTO;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentManagementResponse;
import com.roomrentalmanagementbackend.service.ApartmentService;
import com.roomrentalmanagementbackend.service.ApartmentStatusService;
import com.roomrentalmanagementbackend.service.ApartmentTypeService;
import com.roomrentalmanagementbackend.service.UtilityServiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardController {
    ApartmentService apartmentService;
    ApartmentStatusService apartmentStatusService;
    ApartmentTypeService apartmentTypeService;
    UtilityServiceService utilityServiceService;

    @GetMapping("/apartments")
    public ApiResponse<List<ApartmentManagementResponse>> getApartmentManagement() {
        return ApiResponse.success(apartmentService.getApartmentManagement());
    }

    @GetMapping("/apartments/{slug}")
    public ApiResponse<ApartmentDTO> getApartmentDetail(@PathVariable String slug) {
        return apartmentService.getApartmentDTOBySlug(slug);
    }

    @GetMapping("/statuses")
    public ApiResponse<List<ApartmentStatusDTO>> getAllStatus() {
        return ApiResponse.success(apartmentStatusService.getAllStatus());
    }

    @GetMapping("/types")
    public ApiResponse<List<ApartmentTypeDTO>> getAllType() {
        return ApiResponse.success(apartmentTypeService.getAllType());
    }

    @GetMapping("/services")
    public ApiResponse<List<UtilityServiceDTO>> getAllServices() {
        return ApiResponse.success(utilityServiceService.getAllServicesDTO());
    }
}
