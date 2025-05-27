package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingSearchRequestDTOToBOConverter
        implements Converter<ListingSearchRequestDTO, ListingSearchRequestBO> {
    @Override
    public ListingSearchRequestBO convert(ListingSearchRequestDTO source) {
        return ListingSearchRequestBO.builder()
                .minPrice(source.getMinPrice())
                .maxPrice(source.getMaxPrice())
                .citySubstring(source.getCitySubstring())
                .currency(source.getCurrency())
                .propertyTypes(source.getPropertyTypes())
                .placeTypes(source.getPlaceTypes())
                .amenities(source.getAmenities())
                .numberOfBedrooms(source.getNumberOfBedrooms())
                .numberOfGuests(source.getNumberOfGuests())
                .numberOfBathrooms(source.getNumberOfBathrooms())
                .page(source.getPage())
                .pageSize(source.getPageSize())
                .build();
    }
}
