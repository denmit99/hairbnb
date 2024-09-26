package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingBO {
    private Long id;

    private Long userId;

    private String title;

    private String description;

    private String address;

    private Double pricePerNight;

    private Double pricePerNightUsd;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;

    private Integer maxGuests;

    private Integer numberOfBathrooms;

    private Integer numberOfBedrooms;

    private List<BedroomBO> bedrooms;

    private Set<String> amenities;
}
