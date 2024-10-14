package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Bedroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BedroomRepository extends JpaRepository<Bedroom, UUID> {
    void deleteByListingId(UUID listingId);

    List<Bedroom> findAllByListingId(UUID listingId);
}
