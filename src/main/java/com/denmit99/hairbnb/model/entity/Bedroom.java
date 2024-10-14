package com.denmit99.hairbnb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "bedroom")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bedroom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "listing_id")
    private UUID listingId;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "single_beds_number")
    private Integer singleBedsNum;

    @Column(name = "double_beds_number")
    private Integer doubleBedsNum;

    @Column(name = "queen_beds_number")
    private Integer queenBedsNum;

    @Column(name = "sofa_beds_number")
    private Integer sofaBedsNum;
}
