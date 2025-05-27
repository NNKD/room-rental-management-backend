package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.entity.UtilityService;
import com.roomrentalmanagementbackend.service.UtilityServiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilityServiceController {
    UtilityServiceService utilityServiceService;

    @Operation(description = "Get all services")
    @GetMapping
    public ApiResponse<List<UtilityService>> getUtilityServices() {
        return ApiResponse.success(utilityServiceService.getAllServices());
    }

}
