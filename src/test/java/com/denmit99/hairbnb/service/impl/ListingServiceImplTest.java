package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.model.entity.ListingAmenity;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.BedroomService;
import com.denmit99.hairbnb.service.CurrencyConverter;
import com.denmit99.hairbnb.service.ListingAmenityService;
import com.denmit99.hairbnb.service.UserService;
import com.denmit99.hairbnb.service.converter.ListingToListingBOConverter;
import com.denmit99.hairbnb.util.RandomEnumUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListingServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private BedroomService bedroomService;

    @Mock
    private ListingAmenityService listingAmenityService;

    @Mock
    private UserService userService;

    @Mock
    private CurrencyConverter currencyConverter;

    @Mock
    private ListingToListingBOConverter listingToListingBOConverter;

    @InjectMocks
    private ListingServiceImpl service;

    @Test
    public void create() {
        UserBO user = new UserBO();
        Mockito.when(userService.getCurrent()).thenReturn(user);
        Listing listingEntity = new Listing();
        Mockito.when(listingRepository.save(any()))
                .thenReturn(listingEntity);
        ListingCreateRequestDTO requestDTO = createRequestDTO();

        service.create(requestDTO);

        verify(userService).getCurrent();
        verify(listingRepository).save(any());
        verify(bedroomService).save(any(), any());
        verify(listingAmenityService).save(any(), any());
        verify(listingToListingBOConverter).convert(eq(listingEntity), any(), any());
    }

    @Test
    public void delete() {
        UUID listingId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserBO user = new UserBO();
        user.setId(userId);
        Mockito.when(userService.getCurrent()).thenReturn(user);
        Mockito.when(listingRepository.findById(listingId))
                .thenReturn(Optional.of(createListing(userId)));

        service.delete(listingId);

        verify(bedroomService).deleteByListingId(listingId);
        verify(listingAmenityService).deleteByListingId(listingId);
        verify(listingRepository).deleteById(listingId);
    }

    @Test
    public void deleteListingOfAnotherUser() {
        UUID listingId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserBO user = new UserBO();
        user.setId(userId);
        Mockito.when(userService.getCurrent()).thenReturn(user);
        Mockito.when(listingRepository.findById(listingId))
                .thenReturn(Optional.of(createListing(UUID.randomUUID())));

        assertThrows(AccessDeniedException.class, () -> service.delete(listingId));
    }

    @Test
    public void deleteListingNotFoundThrowsException() {
        UUID listingId = UUID.randomUUID();
        Mockito.when(userService.getCurrent()).thenReturn(new UserBO());
        Mockito.when(listingRepository.findById(listingId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.delete(listingId));
    }

    @Test
    public void get() {
        UUID listingId = UUID.randomUUID();
        Listing listing = new Listing();
        AmenityType amenity = RandomEnumUtils.nextValue(AmenityType.class);
        listing.setAmenities(List.of(ListingAmenity.builder().amenityCode(amenity).build()));
        List<BedroomBO> bedrooms = List.of(new BedroomBO());
        when(listingRepository.findById(listingId))
                .thenReturn(Optional.of(listing));
        when(bedroomService.getByListingId(listingId))
                .thenReturn(bedrooms);

        service.get(listingId);

        verify(listingToListingBOConverter).convert(listing, bedrooms, Set.of(amenity));
    }

    @Test
    public void getListingNotFoundThrowsException() {
        UUID listingId = UUID.randomUUID();
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.get(listingId));
    }

    @Test
    public void search() {
        ListingSearchRequestBO requestBO = ListingSearchRequestBO.builder()
                .maxPrice(RandomUtils.nextLong())
                .minPrice(RandomUtils.nextLong())
                .currency(Currency.EUR)
                .page(0)
                .pageSize(10)
                .build();
        when(currencyConverter.convertToDefault(anyDouble(), any())).thenReturn(RandomUtils.nextDouble());
        when(listingRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());
        service.search(requestBO);
        verify(listingRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Listing createListing(UUID userId) {
        Listing listing = new Listing();
        listing.setUserId(userId);
        return listing;
    }

    private ListingCreateRequestDTO createRequestDTO() {
        return ListingCreateRequestDTO.builder()
                .title(RandomStringUtils.randomAlphabetic(5))
                .description(RandomStringUtils.randomAlphanumeric(10))
                .address(AddressDTO.builder()
                        .street(RandomStringUtils.randomAlphanumeric(5))
                        .city(RandomStringUtils.randomAlphabetic(5))
                        .country(RandomStringUtils.randomAlphabetic(5))
                        .zipCode(RandomStringUtils.randomNumeric(5))
                        .build())
                .maxGuests(1)
                .currency(RandomEnumUtils.nextValue(Currency.class))
                .propertyType(RandomEnumUtils.nextValue(PropertyType.class))
                .placeType(RandomEnumUtils.nextValue(PlaceType.class))
                .numberOfBathrooms(RandomUtils.nextInt())
                .amenities(Set.of(
                        RandomEnumUtils.nextValue(AmenityType.class),
                        RandomEnumUtils.nextValue(AmenityType.class)
                ))
                .pricePerNight(RandomUtils.nextDouble())
                .bedrooms(List.of(BedroomDTO.builder().singleNum(1).build()))
                .build();
    }
}
