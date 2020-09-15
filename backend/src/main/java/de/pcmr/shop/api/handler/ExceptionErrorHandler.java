package de.pcmr.shop.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Map;

/**
 * Exception handler for handling third party exception and assigning HTML error codes.
 *
 * @author Fynn Lohse
 */

@ControllerAdvice
public class ExceptionErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class})
    public void handleBadRequest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintHibernateViolation() {
        Map<String, String> errorMap = Map.of("message", "Der Datensatz wurde nicht gelöscht. Es besteht eine Referenz auf diesen Datansatz.");
        return new ResponseEntity<>(errorMap, HttpStatus.CONFLICT);
    }
}
