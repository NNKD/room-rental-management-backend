package com.roomrentalmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "apartment_information")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double width;
    double height;
    double area;
    int floor;
    double balcony;
    int bedrooms;
    int kitchens;
    int bathrooms;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_id")
    Apartment apartment;

}
