package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HelloController {
    @GetMapping("/")
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello World");
    }
}
