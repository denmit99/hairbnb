package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;

import java.util.List;
import java.util.UUID;

public interface BedroomService {
    List<BedroomBO> save(UUID listingId, List<BedroomDTO> bedrooms);

    List<BedroomBO> getByListingId(UUID listingId);

    void deleteByListingId(UUID listingId);
}
