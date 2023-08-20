package com.ml.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Room {

    @Id
    private Long id; //Room number

    private Integer singleBeds;
    private Integer doubleBeds;
    private Boolean arConditioning;
    private String observation;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
