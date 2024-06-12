package com.denmit99.hairbnb.controller;

import com.denmit99.hairbnb.model.bo.ListingSearchRequestBO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import com.denmit99.hairbnb.model.dto.ListingLightDTO;
import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.validation.ListingIdExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private ConversionService conversionService;

    @PostMapping
    public List<ListingLightDTO> search(@Valid @RequestBody ListingSearchRequestDTO requestDTO) {
        //TODO search listings by filters
        var res = listingService.search(conversionService.convert(requestDTO, ListingSearchRequestBO.class));
        return res.stream()
                .map(l -> conversionService.convert(l, ListingLightDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ListingDTO get(@PathVariable("id") @ListingIdExists Long listingId) {
        return conversionService.convert(listingService.get(listingId), ListingDTO.class);
    }
}
