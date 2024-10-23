package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ListingBOToListingDTOConverter implements Converter<ListingBO, ListingDTO> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ListingDTO convert(ListingBO source) {
        return ListingDTO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .country(source.getCountry())
                .city(source.getCity())
                .street(source.getStreet())
                .houseNumber(source.getHouseNumber())
                .zipCode(source.getZipCode())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .pricePerNight(source.getPricePerNight())
                .currency(source.getCurrency())
                .numberOfBathrooms(source.getNumberOfBathrooms())
                .numberOfGuests(source.getMaxGuests())
                .amenities(source.getAmenities().stream().map(AmenityType::name).collect(Collectors.toSet()))
                .bedrooms(source.getBedrooms().stream()
                        .map(b -> conversionService.convert(b, BedroomDTO.class))
                        .toList())
                .build();
    }
}
