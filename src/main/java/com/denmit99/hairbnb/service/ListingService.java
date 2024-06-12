package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;

import java.util.List;

public interface ListingService {
    ListingBO create(ListingCreateRequestDTO requestDTO);

    void delete(Long listingId);

    ListingBO get(Long listingId);

    List<ListingLightBO> search(ListingSearchRequestBO requestBO);

    List<ListingLightBO> getAllByUserId(Long userId);
}
