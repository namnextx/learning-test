package com.osc.school.exception;

import com.osc.school.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionTranslator {
    private ResponseEntity<Object> badRequest(Object t) {
        return ResponseEntity.badRequest().body(t);
    }

    private ResponseEntity<Object> serverError(Object object) {
        return new ResponseEntity <> (object, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        return badRequest(new ErrorResponse("400", "Invalid data request"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    ResponseEntity<Object> methodArgumentNotValid(ConstraintViolationException methodArgumentNotValidException) {
        return badRequest(new ErrorResponse("400", "Invalid data input"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestValidationException.class)
    @ResponseBody
    ResponseEntity<Object> requestValidationException(RequestValidationException requestValidation) {
        return badRequest(requestValidation.toErrorResponse());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityIsExist.class)
    @ResponseBody
    ResponseEntity<Object> entityIsExist(EntityIsExist entityIsExist) {
        return serverError(entityIsExist.toErrorResponse());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ResponseEntity<Object> methodArgumentNotValid(EntityNotFoundException e) {
        return badRequest(new ErrorResponse("400", "Invalid data"));
    }
}
