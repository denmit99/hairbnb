package com.denmit99.hairbnb.controller.host;

import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import com.denmit99.hairbnb.service.HostListingService;
import com.denmit99.hairbnb.validation.ListingIdExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host/listings")
public class HostListingController {

    @Autowired
    private HostListingService hostListingService;

    @Autowired
    private ConversionService conversionService;

    @PostMapping
    public ListingDTO create(@Valid @RequestBody ListingCreateRequestDTO requestDTO) {
        var res = hostListingService.create(requestDTO);
        return conversionService.convert(res, ListingDTO.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @ListingIdExists Long listingId) {
        hostListingService.delete(listingId);
    }

    @GetMapping
    public String search() {
        return "search";
    }

    @GetMapping("/{id}")
    public ListingDTO get(@PathVariable("id") @ListingIdExists Long listingId) {
        return conversionService.convert(hostListingService.get(listingId), ListingDTO.class);
    }

}
