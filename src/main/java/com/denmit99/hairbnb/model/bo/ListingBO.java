package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class ListingBO {
    private Long id;

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
