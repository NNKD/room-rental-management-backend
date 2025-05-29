package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.apartment.ApartmentStatusDTO;
import com.roomrentalmanagementbackend.entity.ApartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentStatusRepository extends JpaRepository<ApartmentStatus, Integer> {

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentStatusDTO(
                    a.apartmentStatus.id,
                    a.apartmentStatus.name
                )
            FROM Apartment a
            WHERE a.slug = :slug
            """)
    Optional<ApartmentStatusDTO> findApartmentStatusByApartmentSlug(@Param("slug") String slug);

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentStatusDTO(
                    aStatus.id,
                    aStatus.name
                )
            FROM ApartmentStatus aStatus
            """)
    List<ApartmentStatusDTO> findAllApartmentStatus();
}
