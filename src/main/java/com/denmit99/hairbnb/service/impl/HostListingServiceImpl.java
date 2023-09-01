package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.BedArrangement;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.repository.BedArrangementRepository;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.HostListingService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class HostListingServiceImpl implements HostListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private BedArrangementRepository bedArrangementRepository;

    @Autowired
    private ConversionService conversionService;

    @Transactional
    @Override
    public ListingBO create(ListingCreateRequestDTO requestDTO) {
        var listing = Listing.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .address(convertAddressToString(requestDTO.getAddress()))
                .pricePerNight(requestDTO.getPricePerNight())
                .currency(requestDTO.getCurrency())
                .propertyType(requestDTO.getPropertyType())
                .placeType(requestDTO.getPlaceType())
                .maxNumberOfGuests(requestDTO.getMaxGuests())
                .numberOfBathrooms(requestDTO.getNumberOfBathrooms())
                .creationDate(ZonedDateTime.now())
                .updateDate(ZonedDateTime.now())
                .build();
        var res = listingRepository.save(listing);
        saveBedArrangements(res.getId(), requestDTO.getBedrooms());
        return conversionService.convert(res, ListingBO.class);
    }

    private void saveBedArrangements(Long listingId, List<BedroomDTO> bedrooms){
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

    private String convertAddressToString(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return Strings.EMPTY;
        }
        var list = Stream.of(
                        addressDTO.getCountry(),
                        addressDTO.getCity(),
                        addressDTO.getStreet(),
                        addressDTO.getZipCode())
                .filter(Objects::nonNull)
                .toList();
        return String.join(", ", list);
    }
}
