package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.BedroomService;
import com.denmit99.hairbnb.service.ListingAmenityService;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.service.UserService;
import com.denmit99.hairbnb.service.converter.ListingToListingBOConverter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ListingServiceImpl implements ListingService {

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

    @Autowired
    private ListingToListingBOConverter listingToListingBOConverter;

    @Transactional
    @Override
    public ListingBO create(ListingCreateRequestDTO requestDTO) {
        ZonedDateTime now = ZonedDateTime.now();
        UserBO user = userService.getCurrent();
        Listing listing = Listing.builder()
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
        Listing res = listingRepository.save(listing);
        List<BedroomBO> bedroomsBO = bedroomService.save(res.getId(), requestDTO.getBedrooms());
        listingAmenityService.save(res.getId(), requestDTO.getAmenities());
        return listingToListingBOConverter.convert(res, bedroomsBO);
    }

    @Override
    @Transactional
    public void delete(Long listingId) {
        UserBO currentUser = userService.getCurrent();
        Optional<Listing> listing = listingRepository.findById(listingId);
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
        Optional<Listing> res = listingRepository.findById(listingId);
        if (res.isEmpty()) {
            throw new NotFoundException(String.format("Listing with id %s is not found", listingId));
        }
        List<BedroomBO> bedrooms = bedroomService.getByListingId(listingId);
        return listingToListingBOConverter.convert(res.get(), bedrooms);
    }

    @Override
    public List<ListingLightBO> search(ListingSearchRequestBO requestBO) {
        var res = listingRepository.search(
                requestBO.getMinPrice(),
                requestBO.getMaxPrice(),
                requestBO.getPropertyTypes().stream().map(Enum::name).toList(),
                requestBO.getPlaceTypes().stream().map(Enum::name).toList(),
                requestBO.getNumberOfBathrooms(),
                requestBO.getAmenities());
        return res.stream().map(l -> conversionService.convert(l, ListingLightBO.class)).toList();
    }

    @Override
    public List<ListingLightBO> getAllByUserId(Long userId) {
        var listings = listingRepository.findAllByUserId(userId);
        return listings.stream()
                .map(l -> conversionService.convert(l, ListingLightBO.class))
                .toList();
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
