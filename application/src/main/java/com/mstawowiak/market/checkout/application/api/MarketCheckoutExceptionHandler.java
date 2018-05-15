package com.mstawowiak.market.checkout.application.api;

import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class MarketCheckoutExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MarketCheckoutOperationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handle(MarketCheckoutOperationException ex) {
        String message = ex.getMessage();
        log.error("Handle MarketCheckoutOperationException: {}", message);

        return message;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("Handle ConstraintViolationException: {}", message);

        return error(ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }
}
