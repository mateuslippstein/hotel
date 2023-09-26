package com.ml.hotel.model.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
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
