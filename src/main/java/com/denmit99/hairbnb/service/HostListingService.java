package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;

import java.util.List;

public interface HostListingService {
    ListingBO create(ListingCreateRequestDTO requestDTO);

    void delete(Long listingId);

    ListingBO get(Long listingId);

    List<ListingLightBO> getAllByUserId(Long userId);
}
