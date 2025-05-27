package com.roomrentalmanagementbackend.controller;

import com.nimbusds.jose.JOSEException;
import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.request.AuthenticationRequest;
import com.roomrentalmanagementbackend.dto.response.AuthenticationResponse;
import com.roomrentalmanagementbackend.entity.User;
import com.roomrentalmanagementbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody User user) throws JOSEException {
        AuthenticationResponse response = userService.register(user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest request) throws JOSEException {
        AuthenticationResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/admin-emails")
    public ResponseEntity<ApiResponse<Object>> getAdminEmails() {
        return ResponseEntity.ok(ApiResponse.success(userService.getEmailAdmin()));
    }
}