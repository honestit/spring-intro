package io.github.batetolast1.spring.demo.validation.validators;

import io.github.batetolast1.spring.demo.service.ValidationService;
import io.github.batetolast1.spring.demo.validation.constraints.UniqueUsername;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@Log4j2
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final ValidationService validationService;

    @Autowired
    public UniqueUsernameValidator(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        log.debug("Validating unique username: {}", username);
        boolean isUnique = validationService.isUniqueUsername(username);
        log.debug("Is username '{}' unique? {}", username, isUnique);
        if (isUnique) {
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Username should be unique. '" + username + "' is already taken").addConstraintViolation();
            return false;
        }
    }
}
