package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BedroomListingCreateRequestDTO {
    @Min(0)
    @Max(Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer doubleNum;

    @Min(0)
    @Max(Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer singleNum;

    @Min(0)
    @Max(Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer queenNum;

    @Min(0)
    @Max(Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer sofaNum;
}
