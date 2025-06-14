package com.roomrentalmanagementbackend.controller;

import com.nimbusds.jose.JOSEException;
import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.auth.request.AuthenticationRequest;
import com.roomrentalmanagementbackend.dto.auth.request.ForgotPasswordRequest;
import com.roomrentalmanagementbackend.dto.user.request.UserRequestDTO;
import com.roomrentalmanagementbackend.dto.auth.response.AuthenticationResponse;
import com.roomrentalmanagementbackend.dto.user.response.UserResponse;
import com.roomrentalmanagementbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody User user) throws JOSEException {
//        AuthenticationResponse response = userService.register(user);
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest request) throws JOSEException {
        AuthenticationResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Mật khẩu mới đã được gửi tới email của bạn"));
    }
    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ApiResponse.success(users);
    }

    @GetMapping("/admins")
    public ApiResponse<List<UserResponse>> getAllAdmins() {
        List<UserResponse> admins = userService.getAllAdmins();
        return ApiResponse.success(admins);
    }
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequestDTO request) {
        UserResponse user = userService.createUser(request);
        return ApiResponse.success(user);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable int id, @RequestBody UserRequestDTO request) {
        UserResponse user = userService.updateUser(id, request);
        return ApiResponse.success(user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ApiResponse.success("User deleted successfully");
    }
}