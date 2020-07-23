package com.osc.school.model.response;

@FunctionalInterface
public interface ErrorResponseSupport {
    ErrorResponse toErrorResponse();
}
