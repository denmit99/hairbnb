package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.repository.AmenityRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AmenityCodesExistValidator implements ConstraintValidator<AmenityCodesExist, Set<String>> {

    @Autowired
    private AmenityRepository amenityRepository;

    @Override
    public boolean isValid(Set<String> value, ConstraintValidatorContext context) {
  //      var res = amenityRepository.getAllNonExistingCodes(value);
//        return res.isEmpty();
        return true;
    }
}
