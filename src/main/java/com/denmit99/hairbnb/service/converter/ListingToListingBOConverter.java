package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.service.ListingAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingToListingBOConverter implements Converter<Listing, ListingBO> {

    @Autowired
    private ListingAmenityService listingAmenityService;

    @Override
    public ListingBO convert(Listing source) {
        return ListingBO.builder()
                .id(source.getId())
                .userId(source.getUserId())
                .title(source.getTitle())
                .description(source.getDescription())
                .address(source.getAddress())
                .pricePerNight(source.getPricePerNight())
                .currency(source.getCurrency())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .maxGuests(source.getMaxNumberOfGuests())
                .numberOfBathrooms(source.getNumberOfBathrooms())
                .amenities(listingAmenityService.getByListing(source.getId()))
                .build();
    }
}
