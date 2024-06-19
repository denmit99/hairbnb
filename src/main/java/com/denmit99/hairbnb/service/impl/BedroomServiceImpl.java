package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.entity.Bedroom;
import com.denmit99.hairbnb.repository.BedroomRepository;
import com.denmit99.hairbnb.service.BedroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedroomServiceImpl implements BedroomService {

    @Autowired
    private BedroomRepository bedroomRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<BedroomBO> save(Long listingId, List<BedroomDTO> bedrooms) {
        List<Bedroom> entities = new ArrayList<>();
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
        List<Bedroom> savedBedrooms = bedroomRepository.saveAll(entities);
        return savedBedrooms.stream()
                .map(b -> conversionService.convert(b, BedroomBO.class))
                .toList();
    }

    @Override
    public List<BedroomBO> getByListingId(Long listingId) {
        return bedroomRepository.findAllByListingId(listingId)
                .stream()
                .map(b -> conversionService.convert(b, BedroomBO.class))
                .toList();
    }

    @Override
    public void deleteByListingId(Long listingId) {
        bedroomRepository.deleteByListingId(listingId);
    }
}
