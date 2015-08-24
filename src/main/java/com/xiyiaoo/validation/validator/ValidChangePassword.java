package com.xiyiaoo.validation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-14 11:57:53
 */
@Constraint(validatedBy = ChangePasswordValidator.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidChangePassword {
    String message() default "{User.password.change}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
