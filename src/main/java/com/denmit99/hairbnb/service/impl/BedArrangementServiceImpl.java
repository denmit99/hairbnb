package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.entity.BedArrangement;
import com.denmit99.hairbnb.repository.BedArrangementRepository;
import com.denmit99.hairbnb.service.BedArrangementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedArrangementServiceImpl implements BedArrangementService {

    @Autowired
    private BedArrangementRepository bedArrangementRepository;

    @Override
    public void save(Long listingId, List<BedroomDTO> bedrooms) {
        for (int i = 0; i < bedrooms.size(); i++) {
            var bedroom = bedrooms.get(i);
            for (var arrangement : bedroom.getArrangements()) {
                var bedArrangementEntity = BedArrangement.builder().listingId(listingId)
                        .bedType(arrangement.getBedType())
                        .roomNumber(i)
                        .numberOfBeds(arrangement.getNumber())
                        .build();
                bedArrangementRepository.save(bedArrangementEntity);
            }
        }
    }

    @Override
    public void deleteByListingId(Long listingId) {
        bedArrangementRepository.deleteByListingId(listingId);
    }
}
