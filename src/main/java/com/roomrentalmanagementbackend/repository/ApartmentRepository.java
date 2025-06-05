package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.apartment.ApartmentDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentDiscountDTO;
import com.roomrentalmanagementbackend.dto.apartment.ApartmentImageDTO;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentDetailResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentDiscountResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentImageResponse;
import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentManagementResponse;
import com.roomrentalmanagementbackend.entity.Apartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {
    List<Apartment> findByHot(int hot);
    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.response.
                ApartmentDetailResponse(
                    a.name,
                    a.slug,
                    a.brief,
                    a.description,
                    a.price,
                    ai.width,
                    ai.height,
                    ai.floor,
                    ai.balcony,
                    ai.terrace,
                    ai.furniture,
                    ai.bedrooms,
                    ai.kitchens,
                    ai.bathrooms,
                    at.name,
                    null ,
                    null 
                )
            FROM Apartment a
            JOIN a.apartmentInformation ai
            JOIN a.apartmentType at
            WHERE a.slug = :slug
            """)
    ApartmentDetailResponse findApartmentDetailBySlug(@Param("slug") String slug);


    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.response.
                ApartmentImageResponse(
                    ai.url
                ) 
            FROM ApartmentImage ai
            WHERE ai.apartment.slug = :slug
            """)
    List<ApartmentImageResponse> findImagesByApartmentSlug(@Param("slug") String slug);

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentImageDTO(
                    ai.id,
                    ai.url
                ) 
            FROM ApartmentImage ai
            WHERE ai.apartment.slug = :slug
            """)
    List<ApartmentImageDTO> findImagesDTOByApartmentSlug(@Param("slug") String slug);

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.response.
                ApartmentDiscountResponse(
                    ad.discount_percent,
                    ad.duration_month
                ) 
            FROM ApartmentRentalDiscount ad
            WHERE ad.apartment.slug = :slug
            """)
    List<ApartmentDiscountResponse> findDiscountsByApartmentSlug(@Param("slug") String slug);

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentDiscountDTO(
                    ad.id,
                    ad.discount_percent,
                    ad.duration_month
                ) 
            FROM ApartmentRentalDiscount ad
            WHERE ad.apartment.slug = :slug
            """)
    List<ApartmentDiscountDTO> findDiscountsDTOByApartmentSlug(@Param("slug") String slug);

    @Query("SELECT a.name FROM Apartment a WHERE a.slug = :slug")
    Optional<String> findNameBySlug(@Param("slug") String slug);

    @Query("""
        SELECT new com.roomrentalmanagementbackend.dto.apartment.response.
            ApartmentManagementResponse(
                a.id,
                a.name,
                a.slug,
                a.price,
                at.name,
                aStatus.name,
                u.fullname,
                u.email
            )
        FROM Apartment a
        JOIN a.apartmentType at
        JOIN a.apartmentStatus aStatus
        LEFT JOIN a.rentalContracts rc
        LEFT JOIN rc.user u
        ORDER BY a.id ASC
    """)
    List<ApartmentManagementResponse> findAllWithUserAndRentalContractStatus();

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.apartment.
                ApartmentDTO(
                    a.id,
                    a.name,
                    a.slug,
                    a.brief,
                    a.description,
                    a.hot,
                    a.price,
                    null,
                    null,
                    null ,
                    null ,
                    null
                )
            FROM Apartment a
            WHERE a.slug = :slug
            """)
    Optional<ApartmentDTO> findApartmentDTOBySlug(@Param("slug") String slug);
}
