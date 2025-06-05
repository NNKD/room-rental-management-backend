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
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @PostMapping("/apartments")
    public ApiResponse<String> addOrUpdateApartment(@RequestBody @Valid ApartmentDTO apartmentDTO) {
        log.info(apartmentDTO.toString());
        return apartmentService.addOrUpdateApartment(apartmentDTO);
    }

    @DeleteMapping("/apartments/{id}")
    public ApiResponse<String> deleteApartment(@PathVariable String id) {
        log.info(id);
        return apartmentService.deleteApartment(id);
    }

    @PostMapping("/types")
    public ApiResponse<String> addOrUpdateType(@RequestBody @Valid ApartmentTypeDTO apartmentTypeDTO) {
        log.info(apartmentTypeDTO.toString());
        return apartmentTypeService.addOrUpdateType(apartmentTypeDTO);
    }

    @DeleteMapping("/types/{id}")
    public ApiResponse<String> deleteType(@PathVariable String id) {
        return apartmentTypeService.deleteType(id);
    }

    @GetMapping("/types/check-name")
    public ApiResponse<Boolean> checkType(@RequestParam String name) {
        return apartmentTypeService.validType(name);
    }

}
