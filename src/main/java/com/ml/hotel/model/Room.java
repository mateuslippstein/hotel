package com.ml.hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room {

    @Id
    private Long id; //Room number

    private int singleBeds;
    private int doubleBeds;
    private boolean arConditioning;
    private String observation;

}
