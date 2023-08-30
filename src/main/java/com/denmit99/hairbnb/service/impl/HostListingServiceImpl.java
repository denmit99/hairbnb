package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.HostListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class HostListingServiceImpl implements HostListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public ListingBO create(ListingCreateRequestDTO requestDTO) {
        var listing = Listing.builder()
                .address(requestDTO.getAddress())
                .name(requestDTO.getName())
                .propertyType(requestDTO.getPropertyType().name())
                .pricePerNight(requestDTO.getPricePerNight())
                .creationDate(ZonedDateTime.now())
                .updateDate(ZonedDateTime.now())
                .build();
        var res = listingRepository.save(listing);
        return new ListingBO();
    }
}
