package org.gnori.testtaskdigitalbudget.log;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class ServiceLoggerAspect {

  private static final String ERROR_TEXT = "[{}] threw exception: {} with message '{}' from method({})";

  @AfterThrowing(
      pointcut = "execution(* org.gnori.testtaskdigitalbudget.service.access.storage.impl.*.*(..))",
      throwing = "exception"
  )
  public void afterThrowingExceptionFromServiceMethods(Exception exception) {

    logError(exception, ERROR_TEXT, "service");
  }

  @AfterThrowing(
      pointcut = "execution(* org.gnori.testtaskdigitalbudget.service.search.searcher.*.*(..))",
      throwing = "exception"
  )
  public void afterThrowingExceptionFromSearcherMethods(Exception exception) {

    logError(exception, ERROR_TEXT, "searcher");
  }

  private void logError(Exception exception, String textWithFourParams, String classType){

    var methodName = "unknown method";
    var className = String.format("unknown %s", classType);

    var stackTrace = exception.getStackTrace();
    var exceptionMessage = exception.getMessage();
    var exceptionName = exception.getClass().getSimpleName();

    if (stackTrace != null && stackTrace.length > 0 && stackTrace[0] != null) {
      className = stackTrace[0].getClassName();
      methodName = stackTrace[0].getMethodName();
    }

    log.error(textWithFourParams, className, exceptionName, exceptionMessage, methodName);

  }


}
