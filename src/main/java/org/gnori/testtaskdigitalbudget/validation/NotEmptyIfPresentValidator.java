package org.gnori.testtaskdigitalbudget.validation;

import java.util.Collection;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyIfPresentValidator implements ConstraintValidator<NotEmptyIfPresent, Object> {

  @Override
  public boolean isValid(Object field, ConstraintValidatorContext context) {

    if (field == null) return true;

    if (field instanceof CharSequence) {
      var charSequence = (CharSequence) field;
      return 0 != charSequence.length();
    }

    if (field instanceof Collection<?>) {
      var collection = (Collection<?>) field;
      return !collection.isEmpty();
    }

    if (field instanceof Map<?,?>) {
      var map = (Map<?, ?>) field;
      return !map.isEmpty();
    }

    if (field instanceof Object[]) {
      var array = (Object[]) field;
      return 0 != array.length;
    }

    return true;
  }
}
