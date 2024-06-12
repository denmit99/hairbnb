package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.validation.AmenityCodesExist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;
import java.util.Set;

//TODO add validation annotations for all fields
@Data
public class ListingSearchRequestDTO {
    @Min(1)
    private Long minPrice;

    @Max(Integer.MAX_VALUE)
    private Long maxPrice;

    private List<PropertyType> propertyTypes;

    private List<PlaceType> placeTypes;

    private Integer numberOfBedrooms;

    private Integer numberOfBeds;

    private Integer numberOfBathrooms;

    @AmenityCodesExist
    private Set<String> amenities;

}
