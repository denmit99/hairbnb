package com.denmit99.hairbnb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "listing")
@Builder
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "price_per_night")
    private Integer pricePerNight;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "bed_type")
    private String bedType;

    @Column(name = "bed_number")
    private Integer bedNumber;

    @Column(name = "bath_type")
    private String bathType;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

}
