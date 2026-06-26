package com.amirsaleh.library.core.validation.nationalCode;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IranianNationalCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIranianNationalCode {

    String message() default "کد ملی معتبر نمی‌باشد";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}