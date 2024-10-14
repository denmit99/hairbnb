package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.entity.ListingAmenity;
import com.denmit99.hairbnb.repository.ListingAmenityRepository;
import com.denmit99.hairbnb.service.ListingAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ListingAmenityServiceImpl implements ListingAmenityService {

    @Autowired
    private ListingAmenityRepository repository;

    @Override
    public List<ListingAmenity> save(UUID listingId, Set<AmenityType> amenities) {
        var listingAmenityEntities = amenities.stream()
                .map(a -> ListingAmenity.builder()
                        .listingId(listingId)
                        .amenityCode(a)
                        .build()
                ).toList();
        return repository.saveAll(listingAmenityEntities);
    }

    @Override
    public void deleteByListingId(UUID listingId) {
        repository.deleteByListingId(listingId);
    }

    @Override
    public Set<String> getByListing(UUID listingId) {
        return repository.findAllByListingId(listingId);
    }
}
