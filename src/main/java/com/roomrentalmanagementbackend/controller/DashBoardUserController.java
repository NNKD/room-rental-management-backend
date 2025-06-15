package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentStatusDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentTypeDTO;
import com.roomrentalmanagementbackend.dto.apartment.UtilityServiceDTO;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentManagementResponse;
import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
import com.roomrentalmanagementbackend.service.ApartmentService;
import com.roomrentalmanagementbackend.service.ApartmentStatusService;
import com.roomrentalmanagementbackend.service.ApartmentTypeService;
import com.roomrentalmanagementbackend.service.UtilityServiceService;
import com.roomrentalmanagementbackend.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dashboard-user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardUserController {
    ApartmentService apartmentService;

    @GetMapping("/me/apartments")
    public ApiResponse getApartmentUserByUsername(Authentication authentication) {
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        return ApiResponse.success(apartmentService.getApartmentUserManagement(user.getUsername()));
    }

}
