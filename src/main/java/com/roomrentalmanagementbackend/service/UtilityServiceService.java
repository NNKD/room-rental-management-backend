package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.apartment.UtilityServiceDTO;
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

    public List<UtilityServiceDTO> getAllServicesDTO() {
        return utilityServiceRepository.findAllService();
    }


    public UtilityService getServiceById(int id) {
        return utilityServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("serviceNotFound"));
    }

    public UtilityService createService(UtilityService service) {
        return utilityServiceRepository.save(service);
    }

    public UtilityService updateService(int id, UtilityService serviceDetails) {
        UtilityService service = getServiceById(id);
        service.setName(serviceDetails.getName());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        service.setUnit(serviceDetails.getUnit());
        return utilityServiceRepository.save(service);
    }

    public void deleteService(int id) {
        UtilityService service = getServiceById(id);
        utilityServiceRepository.delete(service);
    }
}
