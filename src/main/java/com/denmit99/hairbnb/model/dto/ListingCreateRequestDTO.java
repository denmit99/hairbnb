package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.validation.AmenityCodesExist;
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
public class ListingCreateRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    @Size(max = Constants.DESCRIPTION_MAX_LENGTH)
    private String description;

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
    private Integer maxGuests;

    @NotEmpty
    private List<BedroomDTO> bedrooms;

    @NotNull
    private Integer numberOfBathrooms;

    //TODO check that all are valid
    @AmenityCodesExist
    private Set<String> amenities;

}
