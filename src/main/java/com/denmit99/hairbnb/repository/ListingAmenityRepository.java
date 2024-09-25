package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.ListingAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ListingAmenityRepository extends JpaRepository<ListingAmenity, Long> {
    @Query("SELECT la.amenityCode FROM ListingAmenity la "
            + "WHERE la.listingId = :listingId")
    Set<String> findAllByListingId(Long listingId);

    void deleteByListingId(Long listingId);
}
