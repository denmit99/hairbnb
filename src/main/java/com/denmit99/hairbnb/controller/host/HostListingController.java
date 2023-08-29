package com.denmit99.hairbnb.controller.host;

import com.denmit99.hairbnb.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host/listings")
public class HostListingController {

    @Autowired
    private ListingRepository listingRepository;

    @PostMapping
    public String create() {
        return "create";
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
