package com.denmit99.hairbnb.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressBO {
    private String country;

    private String street;

    private String city;

    private String houseNumber;

    private String zipCode;
}
