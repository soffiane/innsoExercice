package com.innso.serviceClient.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * The interface Enum validator.
 */
@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
public @interface EnumValidator {

    /**
     * Enum clazz class.
     *
     * @return the class
     */
    Class<? extends Enum<?>> enumClazz();

    /**
     * Message string.
     *
     * @return the string
     */
    String message() default "Value is not valid";

    /**
     * Groups class [ ].
     *
     * @return the class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * Payload class [ ].
     *
     * @return the class [ ]
     */
    Class<? extends Payload>[] payload() default {};

}