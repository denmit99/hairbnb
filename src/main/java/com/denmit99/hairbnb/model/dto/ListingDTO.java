package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingDTO {
    private UUID id;

    private String title;

    private String description;

    private String country;

    private String street;

    private String city;

    private String houseNumber;

    private String zipCode;

    private Double pricePerNight;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;

    private List<BedroomDTO> bedrooms;

    private Integer numberOfBathrooms;

    private Integer numberOfGuests;

    private Set<String> amenities;
}
