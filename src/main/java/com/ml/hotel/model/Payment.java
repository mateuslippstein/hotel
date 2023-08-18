package com.ml.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import com.ml.hotel.util.PaymentMethodEnum;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RoomBooking> roomBookings;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

}
