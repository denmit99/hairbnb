package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.service.ListingAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListingToListingBOConverter  {

    @Autowired
    private ListingAmenityService listingAmenityService;

    public ListingBO convert(Listing listing, List<BedroomBO> bedrooms) {
        return ListingBO.builder()
                .id(listing.getId())
                .userId(listing.getUserId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .address(listing.getAddress())
                .pricePerNight(listing.getPricePerNight())
                .currency(listing.getCurrency())
                .propertyType(listing.getPropertyType())
                .placeType(listing.getPlaceType())
                .maxGuests(listing.getMaxNumberOfGuests())
                .numberOfBathrooms(listing.getNumberOfBathrooms())
                .numberOfBedrooms(listing.getNumberOfBedrooms())
                .bedrooms(bedrooms)
                .amenities(listingAmenityService.getByListing(listing.getId()))
                .build();
    }
}
