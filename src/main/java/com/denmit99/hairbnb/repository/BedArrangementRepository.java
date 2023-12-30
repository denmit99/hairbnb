package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.BedArrangement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedArrangementRepository extends JpaRepository<BedArrangement, Long> {
    void deleteByListingId(Long listingId);
}
