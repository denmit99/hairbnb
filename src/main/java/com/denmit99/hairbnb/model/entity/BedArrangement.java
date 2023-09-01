package com.denmit99.hairbnb.model.entity;

import com.denmit99.hairbnb.model.BedType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "bed_arrangement")
@Builder
@Data
public class BedArrangement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "listing_id")
    private Long listingId;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "bed_type")
    @Enumerated(EnumType.STRING)
    private BedType bedType;

    @Column(name = "number_of_beds")
    private Integer numberOfBeds;
}
