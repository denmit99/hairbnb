package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.entity.ListingAmenity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ListingAmenityService {
    List<ListingAmenity> save(UUID listingId, Set<AmenityType> amenities);

    void deleteByListingId(UUID listingId);

    Set<String> getByListing(UUID listingId);
}
