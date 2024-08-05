package rw.global.qt.bloggy.annotations.Constraits;

import rw.global.qt.bloggy.annotations.ValidGender;
import rw.global.qt.bloggy.enums.EGender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, EGender> {

    @Override
    public void initialize(ValidGender constraintAnnotation) {
    }

    @Override
    public boolean isValid(EGender gender, ConstraintValidatorContext context) {
        if (gender == null) {
            return false;
        }

        // Check if the gender is either MALE or FEMALE
        return gender == EGender.MALE || gender == EGender.FEMALE;
    }
}
