package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.entity.Listing;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.BedroomService;
import com.denmit99.hairbnb.service.ListingAmenityService;
import com.denmit99.hairbnb.service.UserService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HostListingServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private BedroomService bedroomService;

    @Mock
    private ListingAmenityService listingAmenityService;

    @Mock
    private UserService userService;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private HostListingServiceImpl service;

    @Test
    public void create() {
        UserBO user = new UserBO();
        Mockito.when(userService.getCurrent()).thenReturn(user);
        Listing listingEntity = new Listing();
        ListingBO listingBO = new ListingBO();
        Mockito.when(listingRepository.save(any()))
                .thenReturn(listingEntity);
        Mockito.when(conversionService.convert(listingEntity, ListingBO.class))
                .thenReturn(listingBO);
        ListingCreateRequestDTO requestDTO = new ListingCreateRequestDTO();

        service.create(requestDTO);

        verify(userService).getCurrent();
        verify(listingRepository).save(any());
        verify(bedroomService).save(any(), any());
        verify(listingAmenityService).save(any(), any());
    }

    @Test
    public void delete() {
        Long listingId = RandomUtils.nextLong();
        Long userId = RandomUtils.nextLong();

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
        Long listingId = RandomUtils.nextLong();
        Long userId = RandomUtils.nextLong();

        UserBO user = new UserBO();
        user.setId(userId);
        Mockito.when(userService.getCurrent()).thenReturn(user);
        Mockito.when(listingRepository.findById(listingId))
                .thenReturn(Optional.of(createListing(userId - 1)));

        assertThrows(AccessDeniedException.class, () -> service.delete(listingId));
    }

    @Test
    public void deleteListingNotFoundThrowsException() {
        Long listingId = RandomUtils.nextLong();
        Mockito.when(userService.getCurrent()).thenReturn(new UserBO());
        Mockito.when(listingRepository.findById(listingId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.delete(listingId));
    }

    @Test
    public void get() {
        Long listingId = RandomUtils.nextLong();
        Listing listing = new Listing();
        when(listingRepository.findById(listingId))
                .thenReturn(Optional.of(listing));

        service.get(listingId);

        verify(conversionService).convert(listing, ListingBO.class);
    }

    @Test
    public void getListingNotFoundThrowsException() {
        Long listingId = RandomUtils.nextLong();
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.get(listingId));
    }

    private Listing createListing(Long userId) {
        Listing listing = new Listing();
        listing.setUserId(userId);
        return listing;
    }
}
