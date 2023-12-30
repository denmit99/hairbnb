package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.Amenity;
import com.denmit99.hairbnb.model.entity.ListingAmenity;
import com.denmit99.hairbnb.repository.AmenityRepository;
import com.denmit99.hairbnb.repository.ListingAmenityRepository;
import com.denmit99.hairbnb.service.ListingAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ListingAmenityServiceImpl implements ListingAmenityService {

    @Autowired
    private ListingAmenityRepository repository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Override
    public List<ListingAmenity> save(Long listingId, Set<String> amenities) {
        List<Amenity> amenityEntities = amenityRepository.getAllByCodes(amenities);
        var listingAmenityEntities = amenityEntities.stream()
                .map(a -> ListingAmenity.builder()
                        .listingId(listingId)
                        .amenityId(a.getId())
                        .build()
                ).toList();
        return repository.saveAll(listingAmenityEntities);
    }

    @Override
    public void deleteByListingId(Long listingId) {
        repository.deleteByListingId(listingId);
    }

    @Override
    public Set<String> getByListing(Long listingId) {
        return repository.findAllByListingId(listingId);
    }
}
