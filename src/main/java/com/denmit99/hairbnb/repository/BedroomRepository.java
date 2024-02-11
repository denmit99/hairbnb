package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Bedroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedroomRepository extends JpaRepository<Bedroom, Long> {
    void deleteByListingId(Long listingId);
}
