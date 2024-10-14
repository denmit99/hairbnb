package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ListingService {
    ListingBO create(ListingCreateRequestDTO requestDTO);

    void delete(UUID listingId);

    ListingBO get(UUID listingId);

    List<ListingLightBO> search(ListingSearchRequestBO requestBO);

    List<ListingLightBO> getAllByUserId(UUID userId);
}
