package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.Bedroom;
import com.denmit99.hairbnb.repository.BedroomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BedroomServiceImplTest {

    @Mock
    private BedroomRepository bedroomRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private BedroomServiceImpl service;

    @Test
    void getByListingId() {
        UUID listingId = UUID.randomUUID();
        when(bedroomRepository.findAllByListingId(listingId))
                .thenReturn(List.of(Bedroom.builder().build()));
        service.getByListingId(listingId);

        verify(bedroomRepository).findAllByListingId(listingId);
    }

    @Test
    void deleteByListingId_InvokesRepository() {
        UUID listingId = UUID.randomUUID();
        service.deleteByListingId(listingId);
        verify(bedroomRepository).deleteByListingId(listingId);
    }

}
