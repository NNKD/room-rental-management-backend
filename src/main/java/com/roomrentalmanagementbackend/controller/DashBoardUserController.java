package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.UserApartmentDetailResponse;

import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;

import com.roomrentalmanagementbackend.dto.payment.request.PaymentRequest;
import com.roomrentalmanagementbackend.dto.user.request.UserAccountPassRequest;
import com.roomrentalmanagementbackend.dto.user.response.UserAccountResponse;
import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
import com.roomrentalmanagementbackend.service.ApartmentService;
import com.roomrentalmanagementbackend.service.BillingService;


import com.roomrentalmanagementbackend.service.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.roomrentalmanagementbackend.service.UserService;
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
    BillingService billingService;
    UserService userService;
    VnPayService vnPayService;

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


    @GetMapping("me/account")
    public ApiResponse<UserAccountResponse> getAccount(Authentication authentication) {
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        return ApiResponse.success(userService.getUserAccount(user.getUsername()));
    }


    @PutMapping("me/account/update-pass")
    public ApiResponse updatePass(@RequestBody @Valid UserAccountPassRequest request, Authentication authentication) {
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        if (!userService.checkPass(user.getUsername(), request.getPass())) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "invalidPassword");
        }

        return userService.updateUserPass(user.getUsername(), request.getNewPass());
    }

    @PostMapping("me/payment")
    public ApiResponse payment(@RequestBody PaymentRequest request,
                               HttpServletRequest servletRequest) {

        return vnPayService.createVnPayPayment(request, servletRequest);
    }



}

