package com.denmit99.hairbnb.model.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BedroomBO {
    private Integer number;
    private Integer doubleNum;
    private Integer singleNum;
    private Integer queenNum;
    private Integer sofaNum;
}
