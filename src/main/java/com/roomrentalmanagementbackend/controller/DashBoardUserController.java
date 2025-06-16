package com.roomrentalmanagementbackend.controller;

import com.cloudinary.Api;
import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.UserApartmentDetailResponse;

import com.roomrentalmanagementbackend.dto.billing.response.BillResponseDTO;

import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
import com.roomrentalmanagementbackend.repository.UserRepository;
import com.roomrentalmanagementbackend.service.ApartmentService;
import com.roomrentalmanagementbackend.service.BillingService;

import com.roomrentalmanagementbackend.dto.user.request.UserAccountPassRequest;
import com.roomrentalmanagementbackend.dto.user.request.UserAccountUsernameRequest;
import com.roomrentalmanagementbackend.dto.user.response.UserAccountResponse;

import com.roomrentalmanagementbackend.service.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dashboard-user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardUserController {
    ApartmentService apartmentService;
    UserRepository userRepository;
    BillingService billingService;
    UserService userService;

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

    @PutMapping("me/account/update-username")
    public ApiResponse updateName(UserAccountUsernameRequest request, Authentication authentication) {
        if (!userService.checkValidUsername(request.getUsername())) {
            ApiResponse.error(HttpStatus.BAD_REQUEST, "Đã tồn tại username");
        }
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        return userService.updateUserAccount(user.getUsername(), request.getUsername());
    }

    @PutMapping("me/account/update-pass")
    public ApiResponse updatePass(UserAccountPassRequest request, Authentication authentication) {
        UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
        if (!userService.checkPass(user.getUsername(), request.getPass())) {
            ApiResponse.error(HttpStatus.BAD_REQUEST, "Mật khẩu không đúng");
        }
        return userService.updateUserPass(user.getUsername(), request.getNewPass());
    }

}

