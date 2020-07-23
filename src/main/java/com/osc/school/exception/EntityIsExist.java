package com.osc.school.exception;


import com.osc.school.model.response.ErrorResponse;
import com.osc.school.model.response.ErrorResponseSupport;

public class EntityIsExist extends RuntimeException implements ErrorResponseSupport {
    public EntityIsExist(String message) {
        super(message);
    }

    public EntityIsExist() {
        super();
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return new ErrorResponse("500", "Entity is exits");
    }
}
