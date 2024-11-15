package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.entity.Listing;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingToListingLightBOConverter implements Converter<Listing, ListingLightBO> {
    @Override
    public ListingLightBO convert(Listing source) {
        return ListingLightBO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .address(String.join(" ", source.getZipCode(), source.getHouseNumber(), source.getStreet(),
                        source.getCity(), source.getCountry()))
                .pricePerNight(source.getPricePerNight())
                .pricePerNightUsd(source.getPricePerNightUsd())
                .currency(source.getCurrency())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .build();
    }
}
