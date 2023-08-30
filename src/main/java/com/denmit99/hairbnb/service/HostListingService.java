package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;

public interface HostListingService {
    ListingBO create(ListingCreateRequestDTO requestDTO);
}
