package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.MinMaxDTO;
import com.roomrentalmanagementbackend.dto.apartment.*;
import com.roomrentalmanagementbackend.dto.apartment.filter.response.FilterDataResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.*;
import com.roomrentalmanagementbackend.entity.Apartment;
import com.roomrentalmanagementbackend.entity.ApartmentInformation;
import com.roomrentalmanagementbackend.entity.ApartmentType;
import com.roomrentalmanagementbackend.enums.RentalStatus;
import com.roomrentalmanagementbackend.repository.ApartmentRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentService {
    ApartmentRepository apartmentRepository;
    ApartmentTypeService apartmentTypeService;
    ApartmentStatusService apartmentStatusService;
    ApartmentInformationService apartmentInformationService;
    ModelMapper modelMapper;

    public List<Apartment> getHotApartments() {
        return apartmentRepository.findByHot(1);
    }
//    Use Specification to make filter
    public Page<Apartment> getAllApartmentsPerPageFilter(Pageable pageable, String name, String type,
                                                         Integer bedroom, Double priceMin, Double priceMax) {
        Specification<Apartment> spec = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }

            if (type != null && !type.isEmpty()) {
                Join<Apartment, ApartmentType> join = root.join("apartmentType", JoinType.INNER);
                predicates.add(cb.like(join.get("name"), "%" + type + "%"));
            }

            if (bedroom != null) {
                Join<Apartment, ApartmentInformation> join = root.join("apartmentInformation", JoinType.INNER);
                predicates.add(cb.equal(join.get("bedrooms"), bedroom));
            }

            if (priceMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), priceMin));
            }

            if (priceMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), priceMax));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

       return apartmentRepository.findAll(spec, pageable);
    }

//    Get filter data (Type, min, max bedroom for showing dropdown frontend)
    public FilterDataResponse getFilterData() {
        MinMaxDTO minMaxBedrooms = apartmentInformationService.getMinMaxBedrooms();

        int minBedroom = minMaxBedrooms != null ? minMaxBedrooms.getMin() : 0;
        int maxBedroom = minMaxBedrooms != null ? minMaxBedrooms.getMax() : 0;

        List<ApartmentTypeResponse> types = apartmentTypeService.getAllApartmentTypes()
                                            .stream().
                                            map(apartmentType -> modelMapper.map(apartmentType, ApartmentTypeResponse.class)).toList();

        return FilterDataResponse.builder()
                .minBedroom(minBedroom)
                .maxBedroom(maxBedroom)
                .types(types).build();

    }

//    Get apartment detail by slug
    public ApiResponse<ApartmentDetailResponse> getApartmentDetail(String slug) {
        ApartmentDetailResponse apartmentDetail = apartmentRepository.findApartmentDetailBySlug(slug);

        if (slug == null || slug.trim().isEmpty()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "Slug không được để trống");
        }

        if (apartmentDetail == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "Slug không phù hợp");
        }

        List<ApartmentImageResponse> apartmentImage = apartmentRepository.findImagesByApartmentSlug(slug);
        List<ApartmentDiscountResponse> apartmentDiscount = apartmentRepository.findDiscountsByApartmentSlug(slug);

        apartmentDetail.setImages(apartmentImage);
        apartmentDetail.setDiscounts(apartmentDiscount);
        return ApiResponse.success(apartmentDetail);
    }

    public Optional<String> getNameBySlug(String slug) {
        return apartmentRepository.findNameBySlug(slug);
    }

    public List<ApartmentManagementResponse> getApartmentManagement() {
        return apartmentRepository.findAllWithUserAndRentalContractStatus();
    }

    public ApiResponse<ApartmentDTO> getApartmentDTOBySlug(String slug) {
        ApartmentDTO apartment = apartmentRepository.findApartmentDTOBySlug(slug).orElse(null);

        if (slug == null || slug.trim().isEmpty()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "Slug không được để trống");
        }

        if (apartment == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "Slug không phù hợp");
        }

        ApartmentTypeDTO apartmentType = apartmentTypeService.getTypeByApartmentSlug(slug);
        ApartmentStatusDTO apartmentStatus = apartmentStatusService.getStatusByApartmentSlug(slug);
        List<ApartmentImageDTO> apartmentImage = apartmentRepository.findImagesDTOByApartmentSlug(slug);
        List<ApartmentDiscountDTO> apartmentDiscount = apartmentRepository.findDiscountsDTOByApartmentSlug(slug);
        ApartmentInformationDTO apartmentInformation = apartmentInformationService.getApartmentInformationByApartmentSlug(slug);

        apartment.setType(apartmentType);
        apartment.setStatus(apartmentStatus);
        apartment.setDiscounts(apartmentDiscount);
        apartment.setImages(apartmentImage);
        apartment.setInformation(apartmentInformation);

        return ApiResponse.success(apartment);

    }
}
