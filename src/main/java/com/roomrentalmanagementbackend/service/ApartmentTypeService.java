package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentTypeDTO;
import com.roomrentalmanagementbackend.entity.ApartmentType;
import com.roomrentalmanagementbackend.repository.ApartmentTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentTypeService {
    ApartmentTypeRepository apartmentTypeRepository;
    ModelMapper modelMapper;

    public List<ApartmentType> getAllApartmentTypes() {
        return apartmentTypeRepository.findAll();
    }

    public ApartmentTypeDTO getTypeByApartmentSlug(String slug) {
        return apartmentTypeRepository.findApartmentTypeByApartmentSlug(slug).orElse(null);
    }

    public List<ApartmentTypeDTO> getAllType() {
        return apartmentTypeRepository.findAllApartmentType();
    }

    public ApiResponse<String> addOrUpdateType(ApartmentTypeDTO apartmentTypeDTO) {
        ApartmentType type = modelMapper.map(apartmentTypeDTO, ApartmentType.class);
        apartmentTypeRepository.save(type);
        return ApiResponse.success("Thành công");
    }

    public ApiResponse<String> deleteType(String id) {
        try {
            int idType = Integer.parseInt(id);
            ApartmentType type = apartmentTypeRepository.findById(idType).orElse(null);
            if (type == null) {
                return ApiResponse.error(HttpStatus.BAD_REQUEST,"Loại căn hộ không tồn tại");
            }
            apartmentTypeRepository.delete(type);
            return ApiResponse.success("Xoá thành công");
        }catch (NumberFormatException e) {
            throw new NumberFormatException("Id phải là số");
        }
    }

    public ApiResponse<Boolean> validType(String name) {
        ApartmentType type = apartmentTypeRepository.findApartmentTypeByName(name).orElse(null);
        if (type != null) {
            return ApiResponse.success(false);
        }
        return ApiResponse.success(true);
    }
}
