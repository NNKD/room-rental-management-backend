package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.entity.ApartmentType;
import com.roomrentalmanagementbackend.repository.ApartmentTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentTypeService {
    ApartmentTypeRepository apartmentTypeRepository;

    public List<ApartmentType> getAllApartmentTypes() {
        return apartmentTypeRepository.findAll();
    }
}
