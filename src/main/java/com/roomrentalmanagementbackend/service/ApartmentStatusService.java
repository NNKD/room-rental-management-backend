package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.apartment.ApartmentStatusDTO;
import com.roomrentalmanagementbackend.repository.ApartmentStatusRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentStatusService {
    ApartmentStatusRepository apartmentStatusRepository;


    public ApartmentStatusDTO getStatusByApartmentSlug(String slug) {
        return apartmentStatusRepository.findApartmentStatusByApartmentSlug(slug).orElse(null);
    }

    public List<ApartmentStatusDTO> getAllStatus() {
        return apartmentStatusRepository.findAllApartmentStatus();
    }
}
