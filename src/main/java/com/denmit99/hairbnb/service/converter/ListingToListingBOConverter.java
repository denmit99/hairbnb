package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.entity.Listing;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ListingToListingBOConverter {

    public ListingBO convert(Listing listing, List<BedroomBO> bedrooms, Set<AmenityType> amenities) {
        return ListingBO.builder()
                .id(listing.getId())
                .userId(listing.getUserId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .country(listing.getCountry())
                .city(listing.getCity())
                .street(listing.getStreet())
                .houseNumber(listing.getHouseNumber())
                .zipCode(listing.getZipCode())
                .pricePerNight(listing.getPricePerNight())
                .pricePerNightUsd(listing.getPricePerNightUsd())
                .currency(listing.getCurrency())
                .propertyType(listing.getPropertyType())
                .placeType(listing.getPlaceType())
                .maxGuests(listing.getMaxNumberOfGuests())
                .numberOfBathrooms(listing.getNumberOfBathrooms())
                .numberOfBedrooms(listing.getNumberOfBedrooms())
                .bedrooms(bedrooms)
                .amenities(amenities)
                .build();
    }
}
