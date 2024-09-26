package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ListingSearchRequestBO {

    private Long minPrice;

    private Long maxPrice;

    private Currency currency;

    private List<PropertyType> propertyTypes;

    private List<PlaceType> placeTypes;

    private Integer numberOfBedrooms;

    private Integer numberOfGuests;

    private Integer numberOfBathrooms;

    private Set<AmenityType> amenities;

    private Integer page;

    private Integer pageSize;
}
