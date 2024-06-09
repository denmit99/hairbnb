package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BedroomBOToBedroomDTOConverter implements Converter<BedroomBO, BedroomDTO> {
    @Override
    public BedroomDTO convert(BedroomBO source) {
        return BedroomDTO.builder()
                .number(source.getNumber())
                .singleNum(source.getSingleNum())
                .doubleNum(source.getDoubleNum())
                .queenNum(source.getQueenNum())
                .sofaNum(source.getSofaNum())
                .build();
    }
}
