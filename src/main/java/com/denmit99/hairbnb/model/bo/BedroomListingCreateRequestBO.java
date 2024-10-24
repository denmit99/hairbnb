package com.denmit99.hairbnb.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedroomListingCreateRequestBO {
    private Integer doubleNum;
    private Integer singleNum;
    private Integer queenNum;
    private Integer sofaNum;
}
