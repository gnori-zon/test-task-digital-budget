package org.gnori.testtaskdigitalbudget.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit test for NotEmptyIfPresentValidator")
class NotEmptyIfPresentValidatorTest {

  private final NotEmptyIfPresentValidator validator = new NotEmptyIfPresentValidator();
  private final ConstraintValidatorContext contextMock = Mockito.mock(ConstraintValidatorContext.class);

  @Test
  void isValidWithNulls() {

    Object nullObject = null;
    String nullString = null;
    int[] nullArray = null;

    Assertions.assertTrue(validator.isValid(nullObject, contextMock));
    Assertions.assertTrue(validator.isValid(nullString, contextMock));
    Assertions.assertTrue(validator.isValid(nullArray, contextMock));
  }

  @Test
  void isValidWithCharSequences() {

    String rawNotEmptyString = "string";
    String rawEmptyString = "";

    StringBuffer rawNotEmptyBuffer = new StringBuffer("buffer");
    StringBuffer rawEmptyBuffer = new StringBuffer();

    StringBuilder rawNotEmptyBuilder = new StringBuilder("builder");
    StringBuilder rawEmptyBuilder = new StringBuilder();

    CharSequence rawNotEmptyCharSequence = "string";
    CharSequence rawEmptyCharSequence = "";

    Assertions.assertTrue(validator.isValid(rawNotEmptyString, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyBuffer, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyBuilder, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyCharSequence, contextMock));

    Assertions.assertFalse(validator.isValid(rawEmptyString, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyBuffer, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyBuilder, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyCharSequence, contextMock));
  }

  @Test
  void isValidWithCollections() {

    ArrayList<String> rawNotEmptyArrayList = new ArrayList<>(List.of("string"));
    ArrayList<String> rawEmptyArrayList =  new ArrayList<>();

    List<Integer> rawNotEmptyList = List.of(1);
    List<Integer> rawEmptyList = List.of();

    Collection<Object> rawNotEmptyCollection = List.of(1, "asd");
    Collection<Object> rawEmptyCollection = Collections.emptyList();

    Assertions.assertTrue(validator.isValid(rawNotEmptyArrayList, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyList, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyCollection, contextMock));

    Assertions.assertFalse(validator.isValid(rawEmptyArrayList, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyList, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyCollection, contextMock));
  }

  @Test
  void isValidWithMaps() {

    HashMap<String,String> rawNotEmptyHashMap = new HashMap<>(Map.of("el1", "el2"));
    HashMap<String,String> rawEmptyHashMap =  new HashMap<>();

    Map<Integer, Integer> rawNotEmptyMap = Map.of(1,2);
    Map<Integer, Integer> rawEmptyMap = Map.of();

    Assertions.assertTrue(validator.isValid(rawNotEmptyHashMap, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyMap, contextMock));

    Assertions.assertFalse(validator.isValid(rawEmptyHashMap, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyMap, contextMock));
  }

  @Test
  void isValidWithArrays() {

    Integer[] rawNotEmptyIntegerArray = new Integer[]{1};
    Integer[] rawEmptyIntegerArray =  new Integer[]{};

    String[] rawNotEmptyStringArray = new String[]{"asd"};
    String[] rawEmptyStringArray = new String[]{};

    Object[] rawNotEmptyObjectArray = new Object[]{1,"asd"};
    Object[] rawEmptyObjectArray = new Object[]{};

    Assertions.assertTrue(validator.isValid(rawNotEmptyIntegerArray, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyStringArray, contextMock));
    Assertions.assertTrue(validator.isValid(rawNotEmptyObjectArray, contextMock));

    Assertions.assertFalse(validator.isValid(rawEmptyIntegerArray, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyStringArray, contextMock));
    Assertions.assertFalse(validator.isValid(rawEmptyObjectArray, contextMock));
  }
}