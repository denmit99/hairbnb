package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.HostListingService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class HostListingServiceImpl implements HostListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ListingBO create(ListingCreateRequestDTO requestDTO) {
        var listing = Listing.builder()
                .title(requestDTO.getTitle())
//                .description(requestDTO.getDescription())
//                .address(convertAddressToString(requestDTO.getAddress()))
//                .pricePerNight(requestDTO.getPricePerNight())
//                .currency(requestDTO.getCurrency())
//                .propertyType(requestDTO.getPropertyType())
//                .placeType(requestDTO.getPlaceType())
//                .maxNumberOfGuests(requestDTO.getMaxGuests())
//                .numberOfBathrooms(requestDTO.getNumberOfBathrooms())
                .creationDate(ZonedDateTime.now())
                .updateDate(ZonedDateTime.now())
                .build();
        var res = listingRepository.save(listing);
        return conversionService.convert(res, ListingBO.class);
    }

    private String convertAddressToString(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return Strings.EMPTY;
        }
        var list = Stream.of(
                        addressDTO.getCountry(),
                        addressDTO.getCity(),
                        addressDTO.getStreet(),
                        addressDTO.getZipCode())
                .filter(Objects::nonNull)
                .toList();
        return String.join(", ", list);
    }
}
