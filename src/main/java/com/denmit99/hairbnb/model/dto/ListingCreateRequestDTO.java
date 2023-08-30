package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Set;

import static com.denmit99.hairbnb.constants.Constants.MAX_PRICE_PER_NIGHT;

@Data
public class ListingCreateRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @Min(1)
    @Max(MAX_PRICE_PER_NIGHT)
    private Integer pricePerNight;

    private String currency;

    private PropertyType propertyType;

    private PlaceType placeType;
    private Integer numberOfGuests;

    private String bedType;

    //TODO do we need it?
    private Integer numberOfBeds;

    //TODO do we need it?
    private Integer numberOfBedrooms;
    private List<BedroomDTO> bedrooms;
    private Integer numberOfBathrooms;
    private Set<String> amenities;

    //SUITABLE FOR
    //DETAILS

    //TODO from to at least 2 hours differe nce
    private Integer checkInFrom;
    private Integer checkInTo;
    private Integer allowToBookBefore;
    private Integer maxBookingInAdvanceMonths;

    //TODO validate
    private Integer minDurationDays;
    private Integer maxDurationDays;

}
