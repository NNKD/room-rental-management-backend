package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.entity.UtilityService;
import com.roomrentalmanagementbackend.repository.UtilityServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilityServiceService {
    UtilityServiceRepository utilityServiceRepository;

    public List<UtilityService> getAllServices() {
        return utilityServiceRepository.findAll();
    }
}
