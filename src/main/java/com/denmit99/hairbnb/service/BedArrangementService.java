package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.dto.BedroomDTO;

import java.util.List;

public interface BedArrangementService {
    void save(Long listingId, List<BedroomDTO> bedrooms);

    void deleteByListingId(Long listingId);
}
