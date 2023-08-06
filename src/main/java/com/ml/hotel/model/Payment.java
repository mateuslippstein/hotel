package com.ml.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.ml.hotel.util.PaymentTypeEnum;

@Getter
@Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roomBooking_id")
    private RoomBooking roomBooking;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentMethod;

}
