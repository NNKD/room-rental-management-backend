package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.entity.Apartment;
import com.roomrentalmanagementbackend.repository.ApartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentService {
    ApartmentRepository apartmentRepository;

    public Page<Apartment> getAllApartments(Pageable pageable) {
       return apartmentRepository.findAll(pageable);
    }
}
