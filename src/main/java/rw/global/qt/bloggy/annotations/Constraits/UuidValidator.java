package rw.global.qt.bloggy.annotations.Constraits;



import rw.global.qt.bloggy.annotations.ValidUUID;
import rw.global.qt.bloggy.exceptions.InvalidUUIDException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UuidValidator implements ConstraintValidator<ValidUUID, String> {
    @Override
    public void initialize(ValidUUID constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException("Invalid UUID: " + value, e);
        }
    }
}
