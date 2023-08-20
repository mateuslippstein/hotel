package com.ml.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ml.hotel.util.RoomStatusEnum;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
