package com.denmit99.hairbnb.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class BedroomDTO {
    private List<SleepingArrangementDTO> arrangements;
}
