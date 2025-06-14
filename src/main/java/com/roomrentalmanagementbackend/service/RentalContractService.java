package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.rentalContract.request.RentalContractRequest;
import com.roomrentalmanagementbackend.dto.rentalContract.response.RentalContractResponse;
import com.roomrentalmanagementbackend.entity.Apartment;
import com.roomrentalmanagementbackend.entity.RentalContract;
import com.roomrentalmanagementbackend.entity.User;
import com.roomrentalmanagementbackend.repository.ApartmentRepository;
import com.roomrentalmanagementbackend.repository.RentalContractRepository;
import com.roomrentalmanagementbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalContractService {
    private final RentalContractRepository rentalContractRepository;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    private RentalContractResponse mapToResponse(RentalContract contract) {
        return RentalContractResponse.builder()
                .id(contract.getId())
                .name(contract.getName())
                .description(contract.getDescription())
                .price(contract.getPrice())
                .status(contract.getStatus())
                .startDate(contract.getStartDate().toLocalDate())
                .endDate(contract.getEndDate().toLocalDate())
                .createdAt(contract.getCreatedAt())
                .userId(contract.getUser().getId())
                .fullname(contract.getUser().getFullname())
                .phone(contract.getUser().getPhone())
                .email(contract.getUser().getEmail())
                .apartmentId(contract.getApartment().getId())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RentalContractResponse> getAllRentalContracts() {
        return rentalContractRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RentalContractResponse> getRentalContractsByUserId(int userId) {
        return rentalContractRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RentalContractResponse getRentalContractById(int id) {
        RentalContract contract = rentalContractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hợp đồng thuê không tìm thấy"));
        return mapToResponse(contract);
    }

    @Transactional
    public RentalContractResponse createRentalContract(RentalContractRequest request) {
        // Kích hoạt lại kiểm tra dữ liệu
//        if (request.getPrice() <= 0) {
//            throw new RuntimeException("greaterthan0");
//        }
//        if (request.getStartDate().isAfter(request.getEndDate())) {
//            throw new RuntimeException("Ngày bắt đầu phải trước ngày kết thúc");
//        }
//        if (!List.of("ACTIVE", "INACTIVE", "EXPIRED").contains(request.getStatus())) {
//            throw new RuntimeException("Trạng thái không hợp lệ");
//        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("userNotFound"));
        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new RuntimeException("apartmentNotFound"));

        RentalContract contract = RentalContract.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(request.getStatus())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createdAt(LocalDateTime.now())
                .user(user)
                .apartment(apartment)
                .build();

        RentalContract savedContract = rentalContractRepository.save(contract);
        return mapToResponse(savedContract);
    }

    @Transactional
    public RentalContractResponse updateRentalContract(int id, RentalContractRequest request) {
        // Kích hoạt lại kiểm tra dữ liệu
//        if (request.getPrice() <= 0) {
//            throw new RuntimeException("greaterthan0");
//        }
//        if (request.getStartDate().isAfter(request.getEndDate())) {
//            throw new RuntimeException("Ngày bắt đầu phải trước ngày kết thúc");
//        }
//        if (!List.of("ACTIVE", "INACTIVE", "EXPIRED").contains(request.getStatus())) {
//            throw new RuntimeException("Trạng thái không hợp lệ");
//        }

        RentalContract contract = rentalContractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("rentalContractNotFound"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("userNotFound"));
        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new RuntimeException("apartmentNotFound"));

        contract.setName(request.getName());
        contract.setDescription(request.getDescription());
        contract.setPrice(request.getPrice());
        contract.setStatus(request.getStatus());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setUser(user);
        contract.setApartment(apartment);

        RentalContract updatedContract = rentalContractRepository.save(contract);
        return mapToResponse(updatedContract);
    }

    @Transactional
    public void deleteRentalContract(int id) {
        RentalContract contract = rentalContractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("rentalContractNotFound"));
        rentalContractRepository.delete(contract);
    }
}