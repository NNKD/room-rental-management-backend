package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.entity.UtilityService;
import com.roomrentalmanagementbackend.service.UtilityServiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ApiResponse<UtilityService> createService(@RequestBody UtilityService service) {
        UtilityService createdService = utilityServiceService.createService(service);
        return ApiResponse.success(createdService);
    }
    @PutMapping("/{id}")
    public ApiResponse<UtilityService> updateService(@PathVariable int id, @RequestBody UtilityService service) {
        try {
            return ApiResponse.success(utilityServiceService.updateService(id, service));
        } catch (RuntimeException e) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "serviceNotFound");
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "error");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteService(@PathVariable int id) {
        try {
            utilityServiceService.deleteService(id);
            return ApiResponse.success(null);
        } catch (RuntimeException e) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "serviceNotFound");
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "error");

        }
    }
}
