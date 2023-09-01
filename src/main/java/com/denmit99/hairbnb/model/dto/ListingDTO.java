package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;

import java.util.List;
import java.util.Set;

public class ListingDTO {
    private String title;

    private String description;

    private AddressDTO address;

    private Integer pricePerNight;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;

    private List<BedroomDTO> bedrooms;

    private Integer numberOfBathrooms;

    private Set<String> amenities;
}
