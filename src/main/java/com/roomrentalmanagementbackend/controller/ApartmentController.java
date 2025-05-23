package com.roomrentalmanagementbackend.controller;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.filter.response.FilterDataResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentListResponse;
import com.roomrentalmanagementbackend.dto.page.response.PageResponse;
import com.roomrentalmanagementbackend.entity.Apartment;
import com.roomrentalmanagementbackend.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apartments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentController {
    ApartmentService apartmentService;
    ModelMapper modelMapper;

    @Operation(description = "Get list apartments per page with filter (apartment name, apartment type, number of bedroom, min price, max price) and sort by price")
    @GetMapping
    public ApiResponse<PageResponse<ApartmentListResponse>> getAllApartments(@RequestParam int page,
                                                                             @RequestParam(required = false) String name,
                                                                             @RequestParam(required = false) String type,
                                                                             @RequestParam(required = false) Integer bedroom,
                                                                             @RequestParam(required = false) Double priceMin,
                                                                             @RequestParam(required = false) Double priceMax,
                                                                             @RequestParam(required = false) String sort) {

        Pageable pageable;
//        Check sort
        if (sort != null && !sort.isEmpty()) {
            Sort sortType = "asc".equalsIgnoreCase(sort) ? Sort.by("price").ascending() : Sort.by("price").descending();
            pageable = PageRequest.of(page - 1, 6, sortType);
        }else {
            pageable = PageRequest.of(page - 1, 6);
        }

        Page<Apartment> apartments = apartmentService.getAllApartmentsPerPageFilter(pageable, name, type, bedroom, priceMin, priceMax);

        PageResponse<ApartmentListResponse> pageResponse = PageResponse.<ApartmentListResponse>builder()
                .list(apartments.map(apartment ->
                        modelMapper.map(apartment, ApartmentListResponse.class)).getContent())
                .pageNumber(apartments.getNumber()+1)
                .pageSize(apartments.getSize())
                .totalElements(apartments.getNumberOfElements())
                .totalPages(apartments.getTotalPages()).build();
        return ApiResponse.success(pageResponse);
    }

    @Operation(description = "Get min, max quantity of bedrooms and types of apartment for showing dropdown filters")
    @GetMapping("/filters")
    public ApiResponse<FilterDataResponse> getFilterData() {
        return ApiResponse.success(apartmentService.getFilterData());
    }
}
