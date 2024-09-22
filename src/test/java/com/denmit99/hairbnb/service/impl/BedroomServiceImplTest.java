package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.Bedroom;
import com.denmit99.hairbnb.repository.BedroomRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.List;

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
        Long listingId = RandomUtils.nextLong();
        when(bedroomRepository.findAllByListingId(listingId))
                .thenReturn(List.of(Bedroom.builder().build()));
        service.getByListingId(listingId);

        verify(bedroomRepository).findAllByListingId(listingId);
    }

    @Test
    void deleteByListingId_InvokesRepository() {
        Long listingId = RandomUtils.nextLong();
        service.deleteByListingId(listingId);
        verify(bedroomRepository).deleteByListingId(listingId);
    }

}
