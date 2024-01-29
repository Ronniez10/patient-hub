package com.neelav.patienthub.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BloodGroupValidator implements ConstraintValidator<BloodGroupConstraint,String>{

    private static List<String> validBloodGroups = Arrays.asList("A+","A-","B+","B-","O+","O-","AB+","AB-");
    @Override
    public void initialize(BloodGroupConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return validBloodGroups.contains(s);
    }
}
