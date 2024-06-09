package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.dto.ListingLightDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingLightBOToListingLightDTOConverter implements Converter<ListingLightBO, ListingLightDTO> {
    @Override
    public ListingLightDTO convert(ListingLightBO source) {
        return ListingLightDTO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .address(source.getAddress())
                .pricePerNight(source.getPricePerNight())
                .currency(source.getCurrency())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .build();
    }
}
