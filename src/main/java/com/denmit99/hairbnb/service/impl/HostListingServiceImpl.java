package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.BedroomService;
import com.denmit99.hairbnb.service.HostListingService;
import com.denmit99.hairbnb.service.ListingAmenityService;
import com.denmit99.hairbnb.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class HostListingServiceImpl implements HostListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private BedroomService bedroomService;

    @Autowired
    private ListingAmenityService listingAmenityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @Transactional
    @Override
    public ListingBO create(ListingCreateRequestDTO requestDTO) {
        var now = ZonedDateTime.now();
        var user = userService.getCurrent();
        var listing = Listing.builder()
                .title(requestDTO.getTitle())
                .userId(user.getId())
                .description(requestDTO.getDescription())
                .address(convertAddressToString(requestDTO.getAddress()))
                .pricePerNight(requestDTO.getPricePerNight())
                .currency(requestDTO.getCurrency())
                .propertyType(requestDTO.getPropertyType())
                .placeType(requestDTO.getPlaceType())
                .maxNumberOfGuests(requestDTO.getMaxGuests())
                .numberOfBathrooms(requestDTO.getNumberOfBathrooms())
                .creationDate(now)
                .updateDate(now)
                .build();
        var res = listingRepository.save(listing);
        bedroomService.save(res.getId(), requestDTO.getBedrooms());
        listingAmenityService.save(res.getId(), requestDTO.getAmenities());
        return conversionService.convert(res, ListingBO.class);
    }

    @Override
    @Transactional
    public void delete(Long listingId) {
        UserBO currentUser = userService.getCurrent();
        var listing = listingRepository.findById(listingId);
        if (listing.isEmpty()) {
            throw new NotFoundException(String.format("Listing with id %s is not found", listingId));
        }
        if (!Objects.equals(currentUser.getId(), listing.get().getUserId())) {
            throw new AccessDeniedException("Wrong user");
        }
        bedroomService.deleteByListingId(listingId);
        listingAmenityService.deleteByListingId(listingId);
        listingRepository.deleteById(listingId);
    }

    @Override
    public ListingBO get(Long listingId) {
        var res = listingRepository.findById(listingId);
        if (res.isEmpty()) {
            throw new NotFoundException(String.format("Listing with id %s is not found", listingId));
        }
        return conversionService.convert(res.get(), ListingBO.class);
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
