package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.validation.ListingSearchRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ListingSearchRequest
public class ListingSearchRequestDTO extends PaginationRequest {
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
