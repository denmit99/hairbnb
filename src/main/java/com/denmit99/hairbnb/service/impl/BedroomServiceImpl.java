package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.entity.Bedroom;
import com.denmit99.hairbnb.repository.BedroomRepository;
import com.denmit99.hairbnb.service.BedroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedroomServiceImpl implements BedroomService {

    @Autowired
    private BedroomRepository bedroomRepository;

    @Override
    public void save(Long listingId, List<BedroomDTO> bedrooms) {
        ArrayList<Bedroom> entities = new ArrayList<>();
        for (int i = 0; i < bedrooms.size(); i++) {
            var bedroom = bedrooms.get(i);
            var bedroomEntity = Bedroom.builder()
                    .listingId(listingId)
                    .roomNumber(i)
                    .singleBedsNum(bedroom.getSingleNum())
                    .doubleBedsNum(bedroom.getDoubleNum())
                    .queenBedsNum(bedroom.getQueenNum())
                    .sofaBedsNum(bedroom.getSofaNum())
                    .build();
            entities.add(bedroomEntity);
        }
        bedroomRepository.saveAll(entities);
    }

    @Override
    public void deleteByListingId(Long listingId) {
        bedroomRepository.deleteByListingId(listingId);
    }
}
