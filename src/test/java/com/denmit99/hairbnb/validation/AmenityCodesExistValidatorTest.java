package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.entity.Amenity;
import com.denmit99.hairbnb.repository.AmenityRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmenityCodesExistValidatorTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityCodesExistValidator validator;

    @Test
    void isValid_AllExist_ReturnsTrue() {
        Set<String> codes = Set.of(
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5));
        ConstraintValidatorContext ctx = Mockito.mock(ConstraintValidatorContext.class);
        when(amenityRepository.getAllByCodeIn(codes)).thenReturn(codes.stream()
                .map(c -> Amenity.builder().code(c).build()).toList());

        assertTrue(validator.isValid(codes, ctx));
    }

    @Test
    void isValid_NotAllExist_ReturnsFalse() {
        Set<String> codes = Set.of(
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5));
        ConstraintValidatorContext ctx = Mockito.mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder ctxBuilder =
                Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(ctx.buildConstraintViolationWithTemplate(any())).thenReturn(ctxBuilder);
        when(amenityRepository.getAllByCodeIn(codes))
                .thenReturn(List.of(Amenity.builder().code(codes.stream().findFirst().get()).build()));

        assertFalse(validator.isValid(codes, ctx));
    }
}
