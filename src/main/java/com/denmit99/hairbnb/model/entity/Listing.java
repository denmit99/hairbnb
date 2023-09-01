package com.denmit99.hairbnb.model.entity;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
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

import java.time.ZonedDateTime;

@Entity
@Table(name = "listing")
@Builder
@Data
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private Integer pricePerNight;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "property_type")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "place_type")
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @Column(name = "max_guests")
    private Integer maxNumberOfGuests;

    @Column(name = "num_of_bathrooms")
    private Integer numberOfBathrooms;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

}
