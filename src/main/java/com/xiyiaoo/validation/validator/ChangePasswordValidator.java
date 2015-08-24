package com.xiyiaoo.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-14 11:59:33
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ChangePasswordValidator implements ConstraintValidator<ValidChangePassword, Object[]> {
    @Override
    public void initialize(ValidChangePassword validChangePassword) {

    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext context) {
        if (objects.length != 2) {
            throw new IllegalArgumentException("必须有两个参数");
        }
        return objects[0] != null && !objects[0].equals(objects[1]);
    }
}
