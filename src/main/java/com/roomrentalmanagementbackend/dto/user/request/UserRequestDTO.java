package com.roomrentalmanagementbackend.dto.user.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String email;
    private String username;
    private String fullname;
    private String phone;
    private String password; // Chỉ dùng khi thêm người dùng
    private int role;
}