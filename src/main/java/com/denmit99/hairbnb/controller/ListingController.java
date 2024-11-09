package com.denmit99.hairbnb.controller;

import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import com.denmit99.hairbnb.model.dto.ListingLightDTO;
import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.validation.ListingIdExists;
import jakarta.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;

    private final ConversionService conversionService;

    public ListingController(ListingService listingService,
                             ConversionService conversionService) {
        this.listingService = listingService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public List<ListingLightDTO> search(@Valid @RequestBody ListingSearchRequestDTO requestDTO) {
        return listingService.search(conversionService.convert(requestDTO, ListingSearchRequestBO.class))
                .stream()
                .map(l -> conversionService.convert(l, ListingLightDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ListingDTO getById(@PathVariable("id") @ListingIdExists UUID listingId) {
        return conversionService.convert(listingService.get(listingId), ListingDTO.class);
    }
}
