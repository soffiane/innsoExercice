package com.innso.serviceClient.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Classe validateur pour l'enum Canal.
 */
public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    /**
     * The Value list.
     */
    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = Stream.of(constraintAnnotation.enumClazz().getEnumConstants()).map(e->e.toString()).collect(toList());
    }

}
