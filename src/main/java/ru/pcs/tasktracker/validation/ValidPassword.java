package ru.pcs.tasktracker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Should be 8-20 characters, includes letters and digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
