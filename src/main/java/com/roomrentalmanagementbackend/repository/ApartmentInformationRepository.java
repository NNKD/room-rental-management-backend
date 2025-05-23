package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.MinMaxDTO;
import com.roomrentalmanagementbackend.entity.ApartmentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentInformationRepository extends JpaRepository<ApartmentInformation, Integer> {
//    use JPQL
    @Query("SELECT new com.roomrentalmanagementbackend.dto.MinMaxDTO(MIN(ai.bedrooms), MAX(ai.bedrooms)) FROM ApartmentInformation ai")
    MinMaxDTO findMinMaxBedroom();

}
