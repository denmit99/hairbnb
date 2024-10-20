package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @NotBlank
    @Size(max = Constants.LISTING_MAX_ADDRESS_LENGTH)
    private String country;

    @NotBlank
    @Size(max = Constants.LISTING_MAX_ADDRESS_LENGTH)
    private String street;

    @NotBlank
    @Size(max = Constants.LISTING_MAX_ADDRESS_LENGTH)
    private String city;

    //TODO houseNumber

    @NotBlank
    @Pattern(regexp = "\\d+")
    @Size(max = Constants.LISTING_MAX_ZIPCODE_LENGTH)
    private String zipCode;
}
