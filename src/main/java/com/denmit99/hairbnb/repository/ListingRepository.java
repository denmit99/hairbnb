package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findAllByUserId(Long userId);

    //TODO add number of bedrooms and beds
    @Query(nativeQuery = true, value = """
            SELECT l.* FROM public.listing l
            JOIN public.listing_amenity la ON la.listing_id = l.id
            JOIN public.amenity a ON a.id = la.amenity_id
            WHERE (:minPrice IS NULL OR price >= :minPrice)
            AND (:maxPrice is NULL OR price <= :maxPrice)
            AND property_type IN :propertyTypes
            AND place_type IN :placeTypes
            AND :numberOfBathrooms IS NULL OR :numberOfBathrooms = 0 OR num_of_bathrooms = :numberOfBathrooms
            AND a.code in :amenities
            """)
    List<Listing> search(Long minPrice,
                         Long maxPrice,
                         List<String> propertyTypes,
                         List<String> placeTypes,
                         Integer numberOfBathrooms,
                         Set<String> amenities);
}
