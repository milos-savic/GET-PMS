package by.get.pms.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by milos on 23-Oct-16.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeveloperValidator.class)
public @interface Developer {
    String message() default "{by.get.pms.validation.Developer.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
