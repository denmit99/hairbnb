package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingCreateRequestBO {

    private String title;

    private String description;

    private AddressBO address;

    private Double pricePerNight;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;

    private Integer maxGuests;

    private List<BedroomListingCreateRequestBO> bedrooms;

    private Integer numberOfBathrooms;

    private Set<AmenityType> amenities;
}
