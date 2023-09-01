package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.Builder;

import java.util.Set;

@Builder
public class ListingBO {
    private String title;

    private String description;

    private String address;

    private Integer pricePerNight;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;

    private Integer maxGuests;

    private Integer numberOfBathrooms;

    private Set<String> amenities;
}
