package ru.pcs.tasktracker.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String>{

    // At least
    // one lowercase or uppercase character,
    // one digit,
    // and length between 8 to 20.
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z]|[A-Z]).{8,20}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return Pattern.compile(PASSWORD_PATTERN)
                .matcher(password).matches();
    }

}