package com.denmit99.hairbnb.controller.host;

import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.bo.ListingCreateRequestBO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import com.denmit99.hairbnb.model.dto.ListingLightDTO;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.service.UserService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/host/listings")
public class HostListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @PostMapping
    public ListingDTO create(@Valid @RequestBody ListingCreateRequestDTO requestDTO) {
        ListingBO res = listingService.create(conversionService.convert(requestDTO, ListingCreateRequestBO.class));
        return conversionService.convert(res, ListingDTO.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @ListingIdExists UUID listingId) {
        listingService.delete(listingId);
    }

    @GetMapping
    public List<ListingLightDTO> getAll() {
        var user = userService.getCurrent();
        var listings = listingService.getAllByUserId(user.getId());
        return listings.stream()
                .map(l -> conversionService.convert(l, ListingLightDTO.class))
                .toList();
    }

}
