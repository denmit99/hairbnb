package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findAllByUserId(Long userId);
}
