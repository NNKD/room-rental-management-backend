package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.UserApartmentDetailResponse;
import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;
import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
import com.roomrentalmanagementbackend.repository.UserRepository;
import com.roomrentalmanagementbackend.service.ApartmentService;
import com.roomrentalmanagementbackend.service.BillingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dashboard-user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardUserController {
    ApartmentService apartmentService;
    private final UserRepository userRepository;
    private final BillingService billingService;

    @GetMapping("/me/apartments")
    public ApiResponse getApartmentUserByUsername(Authentication authentication) {
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        return ApiResponse.success(apartmentService.getApartmentUserManagement(user.getUsername()));
    }

    @GetMapping("me/apartments/{slug}")
    public ApiResponse<UserApartmentDetailResponse> getApartmentDetailUser(@PathVariable String slug, Authentication authentication) {
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        return apartmentService.getApartmentDetailUser(user.getUsername(), slug);
    }

    @GetMapping("/me/bills")
    public ApiResponse<List<BillResponseDTO>> getUserBills(Authentication authentication) {
        UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
        return ApiResponse.success(billingService.getUserBills(userInfo.getUsername()));
    }
}