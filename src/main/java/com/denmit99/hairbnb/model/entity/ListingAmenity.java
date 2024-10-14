package com.denmit99.hairbnb.model.entity;

import com.denmit99.hairbnb.model.AmenityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "listing_amenity")
@AllArgsConstructor
@NoArgsConstructor
public class ListingAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "listing_id")
    private UUID listingId;

    @Column(name = "amenity_code")
    @Enumerated(EnumType.STRING)
    private AmenityType amenityCode;
}
