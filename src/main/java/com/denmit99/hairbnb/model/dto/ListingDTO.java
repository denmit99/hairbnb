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

    private String address;

    private Double pricePerNight;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;

    private List<BedroomDTO> bedrooms;

    private Integer numberOfBathrooms;

    private Set<String> amenities;
}
