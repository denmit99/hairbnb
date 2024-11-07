package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ListingSearchRequestValidator
        implements ConstraintValidator<ListingSearchRequest, ListingSearchRequestDTO> {

    private static final String NO_CURRENCY_MESSAGE =
            "Currency is required when either minPrice or maxPrice is provided";
    private static final String MINPRICE_GREATER_THAN_MAXPRICE_MESSAGE =
            "Parameter maxPrice must be greater or equal than minPrice";

    @Override
    public boolean isValid(ListingSearchRequestDTO requestDTO, ConstraintValidatorContext context) {
        if ((requestDTO.getMinPrice() != null || requestDTO.getMaxPrice() != null)
                && requestDTO.getCurrency() == null) {
            setErrorMessage(context, NO_CURRENCY_MESSAGE);
            return false;
        }
        if (requestDTO.getMinPrice() != null && requestDTO.getMaxPrice() != null
                && requestDTO.getMinPrice() > requestDTO.getMaxPrice()) {
            setErrorMessage(context, MINPRICE_GREATER_THAN_MAXPRICE_MESSAGE);
            return false;
        }
        return true;
    }

    private void setErrorMessage(ConstraintValidatorContext context, String errorMsg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMsg)
                .addConstraintViolation();
    }
}
