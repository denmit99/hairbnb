package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID>, JpaSpecificationExecutor<Listing> {
    List<Listing> findAllByUserId(UUID userId);

}
