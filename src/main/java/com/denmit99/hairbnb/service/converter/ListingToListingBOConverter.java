package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.entity.Listing;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;


public class ListingToListingBOConverter implements Converter<Listing, ListingBO> {
    @Override
    public ListingBO convert(Listing source) {
        return ListingBO.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .address(source.getAddress())
                .pricePerNight(source.getPricePerNight())
                .currency(source.getCurrency())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .maxGuests(source.getMaxNumberOfGuests())
                .numberOfBathrooms(source.getNumberOfBathrooms())
                //TODO
                .amenities(Set.of())
                .build();
    }
}
