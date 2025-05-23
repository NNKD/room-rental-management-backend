package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.MinMaxDTO;
import com.roomrentalmanagementbackend.repository.ApartmentInformationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentInformationService {
    ApartmentInformationRepository apartmentInformationRepository;

    public MinMaxDTO getMinMaxBedrooms() {
        return apartmentInformationRepository.findMinMaxBedroom();
    }
}
