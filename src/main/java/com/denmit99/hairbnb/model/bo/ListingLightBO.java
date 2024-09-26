package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingLightBO {
    private Long id;

    private String title;

    private String description;

    private String address;

    private Double pricePerNight;

    private Currency currency;

    private PropertyType propertyType;

    private PlaceType placeType;
}
