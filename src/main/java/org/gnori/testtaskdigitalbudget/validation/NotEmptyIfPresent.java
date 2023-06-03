package org.gnori.testtaskdigitalbudget.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyIfPresentValidator.class)
public @interface NotEmptyIfPresent {

  String message() default "field must is not empty if it is not null";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
