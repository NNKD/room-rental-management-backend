package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.apartment.ApartmentTypeDTO;
import com.roomrentalmanagementbackend.entity.ApartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentTypeRepository extends JpaRepository<ApartmentType, Integer> {
    List<ApartmentType> findAll();

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentTypeDTO(
                    a.apartmentType.id,
                    a.apartmentType.name,
                    a.apartmentType.description
                )
            FROM Apartment a
            WHERE a.slug = :slug
            """)
    Optional<ApartmentTypeDTO> findApartmentTypeByApartmentSlug(@Param("slug") String slug);

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentTypeDTO(
                    at.id,
                    at.name,
                    at.description
                )
            FROM ApartmentType at
            """)
    List<ApartmentTypeDTO> findAllApartmentType();
}
