package com.denmit99.hairbnb.repository.specification;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.model.entity.ListingAmenity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public final class ListingSpecification {

    private ListingSpecification() {
    }

    public static Specification<Listing> hasAmenities(Set<AmenityType> amenities) {
        return (root, query, builder) -> {
            if (amenities == null || amenities.isEmpty()) {
                return builder.conjunction();
            }

            Join<Listing, ListingAmenity> amenitiesJoin = root.join("amenities", JoinType.INNER);
            query.groupBy(root.get("id"));
            query.having(builder.equal(builder.count(amenitiesJoin), amenities.size()));

            return builder.and(amenitiesJoin.get("amenityCode").in(amenities));
        };
    }

    public static Specification<Listing> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, builder) -> {
            if (minPrice != null && maxPrice != null) {
                return builder.between(root.get("pricePerNightUsd"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return builder.ge(root.get("pricePerNightUsd"), minPrice);
            } else if (maxPrice != null) {
                return builder.le(root.get("pricePerNightUsd"), maxPrice);
            }
            return builder.conjunction();
        };
    }

    public static Specification<Listing> availableForNumberOfGuests(Integer numberOfGuests) {
        return (root, query, builder) -> {
            if (numberOfGuests == null) {
                return builder.conjunction();
            }
            return builder.ge(root.get("maxNumberOfGuests"), numberOfGuests);
        };
    }

    public static Specification<Listing> hasBathrooms(Integer numberOfBathrooms) {
        return (root, query, builder) -> {
            if (numberOfBathrooms == null) {
                return builder.conjunction();
            }
            return builder.ge(root.get("numberOfBathrooms"), numberOfBathrooms);
        };
    }

    public static Specification<Listing> hasBedrooms(Integer numberOfBedrooms) {
        return (root, query, builder) -> {
            if (numberOfBedrooms == null) {
                return builder.conjunction();
            }
            return builder.ge(root.get("numberOfBedrooms"), numberOfBedrooms);
        };
    }

    public static Specification<Listing> hasPropertyType(List<PropertyType> propertyTypes) {
        return (root, query, builder) -> {
            if (propertyTypes == null || propertyTypes.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("propertyType").in(propertyTypes);
        };
    }

    public static Specification<Listing> hasPlaceType(List<PlaceType> placeTypes) {
        return (root, query, builder) -> {
            if (placeTypes == null || placeTypes.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("placeType").in(placeTypes);
        };
    }


}
