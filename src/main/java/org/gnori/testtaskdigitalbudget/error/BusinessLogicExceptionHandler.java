package org.gnori.testtaskdigitalbudget.error;

import java.util.HashMap;
import org.gnori.testtaskdigitalbudget.error.exception.BusinessLogicException;
import org.gnori.testtaskdigitalbudget.model.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BusinessLogicExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAuthenticationException(Exception ex) {

    var errorDto = new ErrorDto();
    var status = HttpStatus.INTERNAL_SERVER_ERROR;

    if (ex instanceof BusinessLogicException) {

      var businessLogicException = (BusinessLogicException) ex;
      status = businessLogicException.getStatus();

      errorDto.setError(status.getReasonPhrase());
      errorDto.setErrorDescription(businessLogicException.getMessage());

    } else if (ex instanceof MethodArgumentNotValidException) {

      var validationException = (MethodArgumentNotValidException) ex;

      status = HttpStatus.BAD_REQUEST;
      var fieldsErrors = new HashMap<String, String>();
      validationException.getBindingResult()
          .getFieldErrors()
          .forEach(error -> fieldsErrors.put(error.getField(), error.getDefaultMessage()));

      errorDto.setError(status.getReasonPhrase());
      errorDto.setFieldsErrors(fieldsErrors);

    } else if (
        ex instanceof MissingRequestHeaderException || ex instanceof HttpMessageNotReadableException
    ) {

      status = HttpStatus.BAD_REQUEST;
      errorDto.setError(status.getReasonPhrase());

      if (ex instanceof MissingRequestHeaderException) {
        errorDto.setErrorDescription("missing header");
      } else {
        errorDto.setErrorDescription("not readable body");
      }

    } else {

      errorDto.setError(status.getReasonPhrase());
      errorDto.setErrorDescription("INTERNAL_ERROR");
    }

    return ResponseEntity
        .status(status)
        .body(errorDto);
  }

}
