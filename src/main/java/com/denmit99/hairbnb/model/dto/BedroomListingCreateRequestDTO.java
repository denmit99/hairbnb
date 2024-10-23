package com.denmit99.hairbnb.model.dto;

import com.denmit99.hairbnb.constants.Constants;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BedroomListingCreateRequestDTO {
    @Size(max = Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer doubleNum;

    @Size(max = Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer singleNum;

    @Size(max = Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer queenNum;

    @Size(max = Constants.LISTING_MAX_BEDS_PER_ROOM)
    private Integer sofaNum;
}
