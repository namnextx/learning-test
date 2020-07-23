package com.osc.school.exception;

import com.osc.school.model.response.ErrorResponse;
import com.osc.school.model.response.ErrorResponseSupport;

public class RequestValidationException extends RuntimeException implements ErrorResponseSupport {
    public RequestValidationException() {
        super();
    }

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestValidationException(Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return new ErrorResponse("500", "Entity is exits");
    }
}
