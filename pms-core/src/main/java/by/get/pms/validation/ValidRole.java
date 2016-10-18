package by.get.pms.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRoleValidator.class)
public @interface ValidRole {

    String message() default "{by.get.pms.validation.ValidRole.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
