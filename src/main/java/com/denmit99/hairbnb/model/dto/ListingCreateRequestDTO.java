package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.validation.ListingCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@ListingCreateRequest
public class ListingCreateRequestDTO {
    @NotBlank
    @Size(max = Constants.LISTING_TITLE_MAX_LENGTH)
    private String title;

    @NotBlank
    @Size(max = Constants.LISTING_DESCRIPTION_MAX_LENGTH)
    private String description;

    @Valid
    @NotNull
    private AddressDTO address;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @NotNull
    private Integer pricePerNight;

    @NotNull
    private Currency currency;

    @NotNull
    private PropertyType propertyType;

    @NotNull
    private PlaceType placeType;

    @NotNull
    @Min(1)
    @Max(Constants.LISTING_MAX_MAX_GUESTS)
    private Integer maxGuests;

    @NotEmpty
    @Size(max = Constants.LISTING_MAX_BEDROOMS)
    private List<BedroomDTO> bedrooms;

    @NotNull
    @Min(0)
    @Max(Constants.LISTING_MAX_BATHROOMS)
    private Integer numberOfBathrooms;

    private Set<AmenityType> amenities;

}
