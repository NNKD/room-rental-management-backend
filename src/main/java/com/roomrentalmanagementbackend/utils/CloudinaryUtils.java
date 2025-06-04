package com.roomrentalmanagementbackend.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentImageDTO;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudinaryUtils {
    @Value("${cloudinary.cloud_name}")
    String CLOUD_NAME;
    @Value("${cloudinary.api_key}")
    String API_KEY;
    @Value("${cloudinary.api_secret}")
    String API_SECRET;
    Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        Map<String, Object> config = ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET);
        cloudinary = new Cloudinary(config);
    }

    public String deleteImg(String publicId) throws IOException {
        Map result = cloudinary.uploader().destroy(publicId,
                ObjectUtils.asMap("resource_type", "image"));
        String status = (String) result.get("result");
        return status;
    }

    public ApiResponse<String> deleteListImg(List<ApartmentImageDTO> images) throws IOException {
        try {
            for (ApartmentImageDTO image : images) {
                String status = deleteImg(image.getUrl());
                log.info("Delete status: {}", status);
            }
            return ApiResponse.success("Xoá thành công");
        } catch (Exception e) {
            log.error("Lỗi khi xoá ảnh", e);
            return ApiResponse.error(HttpStatus.BAD_REQUEST,"Lỗi khi xoá ảnh: " + e.getMessage());
        }
    }
}
