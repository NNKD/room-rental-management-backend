package com.roomrentalmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "apartment_rental_discount")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentRentalDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double discount_percent;
    int duration_month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    Apartment apartment;
}
