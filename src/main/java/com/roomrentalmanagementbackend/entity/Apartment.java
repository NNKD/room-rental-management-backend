package com.roomrentalmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "apartment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String slug;
    String brief;
    String description;
    int hot;
    double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    ApartmentType apartmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    ApartmentStatus apartmentStatus;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ApartmentRentalDiscount> discounts;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ApartmentImage> images;

    @OneToOne(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    ApartmentInformation apartmentInformation;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RentalContract> rentalContracts;
}
