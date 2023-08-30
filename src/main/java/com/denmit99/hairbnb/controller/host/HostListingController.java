package com.denmit99.hairbnb.controller.host;

import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.service.HostListingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host/listings")
public class HostListingController {

    @Autowired
    private HostListingService hostListingService;

    @PostMapping
    public String create(@Valid @RequestBody ListingCreateRequestDTO requestDTO) {
        var res = hostListingService.create(requestDTO);
        return "Success";
    }

    @GetMapping
    public String search() {
        return "search";
    }

    @GetMapping("/{id}")
    public String get() {
        return "get";
    }

}
