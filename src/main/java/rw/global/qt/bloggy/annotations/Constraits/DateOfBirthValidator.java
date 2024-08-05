package rw.global.qt.bloggy.annotations.Constraits;

import rw.global.qt.bloggy.annotations.ValidDateOfBirth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {

    @Override
    public void initialize(ValidDateOfBirth constraintAnnotation) {
    }


    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }

        LocalDate now = LocalDate.now();

        // Check if the date is in the future or more than 150 years in the past
        return !dateOfBirth.isAfter(now) && Period.between(dateOfBirth, now).getYears() <= 150;
    }
}