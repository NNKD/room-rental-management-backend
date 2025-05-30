package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.MinMaxDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentInformationDTO;
import com.roomrentalmanagementbackend.entity.ApartmentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentInformationRepository extends JpaRepository<ApartmentInformation, Integer> {
//    use JPQL
    @Query("SELECT new com.roomrentalmanagementbackend.dto.MinMaxDTO(MIN(ai.bedrooms), MAX(ai.bedrooms)) FROM ApartmentInformation ai")
    MinMaxDTO findMinMaxBedroom();

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment
                .ApartmentInformationDTO (
                    ai.id,
                    ai.width,
                    ai.height,
                    ai.floor,
                    ai.balcony,
                    ai.terrace,
                    ai.furniture,
                    ai.bedrooms,
                    ai.kitchens,
                    ai.bathrooms
                )
            FROM ApartmentInformation ai
            WHERE ai.apartment.slug = :slug
            """)
    Optional<ApartmentInformationDTO> findApartmentInformationByApartmentSlug(@Param("slug") String slug);

}
