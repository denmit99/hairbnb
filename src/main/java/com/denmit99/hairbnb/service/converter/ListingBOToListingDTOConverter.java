package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingBOToListingDTOConverter implements Converter<ListingBO, ListingDTO> {
    @Override
    public ListingDTO convert(ListingBO source) {
        return ListingDTO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .address(source.getAddress())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .pricePerNight(source.getPricePerNight())
                .currency(source.getCurrency())
                .numberOfBathrooms(source.getNumberOfBathrooms())
                .amenities(source.getAmenities())
                .build();
    }
}