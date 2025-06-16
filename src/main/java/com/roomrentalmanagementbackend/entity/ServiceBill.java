package com.roomrentalmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "price")
    double price;

    @Column(name = "total_price")
    double totalPrice;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "due_date")
    LocalDateTime dueDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_contract_id")
    RentalContract rentalContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    UtilityService utilityService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    Payment payment;
}