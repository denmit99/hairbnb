package com.denmit99.hairbnb.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BedroomDTO {
    private Integer number;
    private Integer doubleNum;
    private Integer singleNum;
    private Integer queenNum;
    private Integer sofaNum;
}
