package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.BedroomBO;
import com.denmit99.hairbnb.model.entity.Bedroom;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BedroomToBedroomBOConverter implements Converter<Bedroom, BedroomBO> {
    @Override
    public BedroomBO convert(Bedroom source) {
        return BedroomBO.builder()
                .number(source.getRoomNumber())
                .singleNum(source.getSingleBedsNum())
                .doubleNum(source.getDoubleBedsNum())
                .queenNum(source.getQueenBedsNum())
                .sofaNum(source.getSofaBedsNum())
                .build();
    }
}
