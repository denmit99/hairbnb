package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
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

    private Currency currency;

    private List<PropertyType> propertyTypes;

    private List<PlaceType> placeTypes;

    @Min(0)
    @Max(Constants.LISTING_MAX_BEDROOMS)
    private Integer numberOfBedrooms;

    @Min(0)
    @Max(Constants.LISTING_MAX_MAX_GUESTS)
    private Integer numberOfGuests;

    @Min(0)
    @Max(Constants.LISTING_MAX_BATHROOMS)
    private Integer numberOfBathrooms;

    private Set<AmenityType> amenities;

}
