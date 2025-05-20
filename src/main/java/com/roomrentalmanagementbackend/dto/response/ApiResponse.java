package com.roomrentalmanagementbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T>{
    int statusCode; // HTTP Code
    String status; // success or error
    String message;
    T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(HttpStatus.OK.value(), "success", "Operation successful", data);
    }

    public static <T> ApiResponse<T> error(HttpStatus httpStatus, String message) {
        return new ApiResponse<T>(httpStatus.value(), "error", message, null);
    }

}
