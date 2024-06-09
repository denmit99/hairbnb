package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;

import java.util.List;

public interface BedroomService {
    List<BedroomBO> save(Long listingId, List<BedroomDTO> bedrooms);

    List<BedroomBO> getByListingId(Long listingId);

    void deleteByListingId(Long listingId);
}
