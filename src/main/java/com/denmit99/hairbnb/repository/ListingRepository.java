package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
