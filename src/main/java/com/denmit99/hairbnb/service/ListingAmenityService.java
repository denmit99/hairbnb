package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.entity.ListingAmenity;

import java.util.List;
import java.util.Set;

public interface ListingAmenityService {
    List<ListingAmenity> save(Long listingId, Set<String> amenities);

    Set<String> getByListing(Long listingId);
}
