package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.ListingCreateRequestBO;
import com.denmit99.hairbnb.model.bo.ListingLightBO;
import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.model.entity.ListingAmenity;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.repository.specification.ListingSpecification;
import com.denmit99.hairbnb.service.BedroomService;
import com.denmit99.hairbnb.service.CurrencyConverter;
import com.denmit99.hairbnb.service.ListingAmenityService;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.service.UserService;
import com.denmit99.hairbnb.service.converter.ListingToListingBOConverter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ListingServiceImpl implements ListingService {

    private static final double HUNDRED = 100.0;

    private final ListingRepository listingRepository;

    private final BedroomService bedroomService;

    private final ListingAmenityService listingAmenityService;

    private final UserService userService;

    private final ConversionService conversionService;

    private final ListingToListingBOConverter listingToListingBOConverter;

    private final CurrencyConverter currencyConverter;

    public ListingServiceImpl(ListingRepository listingRepository,
                              BedroomService bedroomService,
                              ListingAmenityService listingAmenityService,
                              UserService userService,
                              ConversionService conversionService,
                              ListingToListingBOConverter listingToListingBOConverter,
                              CurrencyConverter currencyConverter) {
        this.listingRepository = listingRepository;
        this.bedroomService = bedroomService;
        this.listingAmenityService = listingAmenityService;
        this.userService = userService;
        this.conversionService = conversionService;
        this.listingToListingBOConverter = listingToListingBOConverter;
        this.currencyConverter = currencyConverter;
    }

    @Transactional
    @Override
    public ListingBO create(ListingCreateRequestBO requestBO) {
        ZonedDateTime now = ZonedDateTime.now();
        UserBO user = userService.getCurrent();
        Listing listing = Listing.builder()
                .title(requestBO.getTitle())
                .userId(user.getId())
                .description(requestBO.getDescription())
                .country(requestBO.getAddress().getCountry())
                .city(requestBO.getAddress().getCity())
                .street(requestBO.getAddress().getStreet())
                .houseNumber(requestBO.getAddress().getHouseNumber())
                .zipCode(requestBO.getAddress().getZipCode())
                .pricePerNight(requestBO.getPricePerNight())
                .pricePerNightUsd(currencyConverter.convertToDefault(
                        requestBO.getPricePerNight(),
                        requestBO.getCurrency())
                )
                .currency(requestBO.getCurrency())
                .propertyType(requestBO.getPropertyType())
                .placeType(requestBO.getPlaceType())
                .maxNumberOfGuests(requestBO.getMaxGuests())
                .numberOfBathrooms(requestBO.getNumberOfBathrooms())
                .numberOfBedrooms(requestBO.getBedrooms().size())
                .creationDate(now)
                .updateDate(now)
                .build();
        Listing res = listingRepository.save(listing);
        List<BedroomBO> bedroomsBO = bedroomService.save(res.getId(), requestBO.getBedrooms());
        listingAmenityService.save(res.getId(), requestBO.getAmenities());
        return listingToListingBOConverter.convert(res, bedroomsBO, requestBO.getAmenities());
    }

    @Override
    @Transactional
    public void delete(UUID listingId) {
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
    public ListingBO get(UUID listingId) {
        Optional<Listing> res = listingRepository.findById(listingId);
        if (res.isEmpty()) {
            throw new NotFoundException(String.format("Listing with id %s is not found", listingId));
        }
        List<BedroomBO> bedrooms = bedroomService.getByListingId(listingId);
        var amenities = res.get().getAmenities()
                .stream().map(ListingAmenity::getAmenityCode).collect(Collectors.toSet());
        return listingToListingBOConverter.convert(res.get(), bedrooms, amenities);
    }

    @Override
    public List<ListingLightBO> search(ListingSearchRequestBO requestBO) {
        Double minPriceUsd = Optional.ofNullable(requestBO.getMinPrice())
                .map(price -> currencyConverter.convertToDefault(price, requestBO.getCurrency()))
                .orElse(null);
        Double maxPriceUsd = Optional.ofNullable(requestBO.getMaxPrice())
                .map(price -> currencyConverter.convertToDefault(price, requestBO.getCurrency()))
                .orElse(null);
        Specification<Listing> specification = Specification
                .where(ListingSpecification.hasAmenities(requestBO.getAmenities()))
                .and(ListingSpecification.hasPriceBetween(minPriceUsd, maxPriceUsd))
                .and(ListingSpecification.availableForNumberOfGuests(requestBO.getNumberOfGuests()))
                .and(ListingSpecification.hasBathrooms(requestBO.getNumberOfBathrooms()))
                .and(ListingSpecification.hasPropertyType(requestBO.getPropertyTypes()))
                .and(ListingSpecification.hasPlaceType(requestBO.getPlaceTypes()))
                .and(ListingSpecification.hasBedrooms(requestBO.getNumberOfBedrooms()));
        return listingRepository
                .findAll(specification, PageRequest.of(requestBO.getPage(), requestBO.getPageSize()))
                .stream()
                .map(l -> {
                    ListingLightBO listingBO = conversionService.convert(l, ListingLightBO.class);
                    convertPriceToUsersCurrency(requestBO.getCurrency(), listingBO);
                    return listingBO;
                })
                .toList();
    }

    @Override
    public List<ListingLightBO> getAllByUserId(UUID userId) {
        var listings = listingRepository.findAllByUserId(userId);
        return listings.stream()
                .map(l -> conversionService.convert(l, ListingLightBO.class))
                .toList();
    }

    private void convertPriceToUsersCurrency(Currency currency, ListingLightBO listingBO) {
        var priceConverted = currencyConverter.convertFromDefault(listingBO.getPricePerNightUsd(), currency);
        var priceRounded = Math.round(priceConverted * HUNDRED) / HUNDRED;
        listingBO.setPricePerNight(priceRounded);
        listingBO.setCurrency(currency);
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
