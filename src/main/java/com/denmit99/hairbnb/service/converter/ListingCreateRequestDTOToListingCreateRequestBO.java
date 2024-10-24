package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.AddressBO;
import com.denmit99.hairbnb.model.bo.BedroomListingCreateRequestBO;
import com.denmit99.hairbnb.model.bo.ListingCreateRequestBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingCreateRequestDTOToListingCreateRequestBO
        implements Converter<ListingCreateRequestDTO, ListingCreateRequestBO> {
    @Override
    public ListingCreateRequestBO convert(ListingCreateRequestDTO source) {
        return ListingCreateRequestBO.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .address(AddressBO.builder()
                        .country(source.getAddress().getCountry())
                        .city(source.getAddress().getCity())
                        .street(source.getAddress().getStreet())
                        .houseNumber(source.getAddress().getHouseNumber())
                        .zipCode(source.getAddress().getZipCode())
                        .build())
                .pricePerNight(source.getPricePerNight())
                .currency(source.getCurrency())
                .propertyType(source.getPropertyType())
                .placeType(source.getPlaceType())
                .maxGuests(source.getMaxGuests())
                .bedrooms(source.getBedrooms().stream()
                        .map(b -> BedroomListingCreateRequestBO.builder()
                                .doubleNum(b.getDoubleNum())
                                .sofaNum(b.getSofaNum())
                                .singleNum(b.getSingleNum())
                                .queenNum(b.getQueenNum())
                                .build())
                        .toList())
                .numberOfBathrooms(source.getNumberOfBathrooms())
                .amenities(source.getAmenities())
                .build();
    }
}
