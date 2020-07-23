package com.osc.school.model.response;

import com.osc.school.constant.MessageCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse extends MessageCode {

    public ErrorResponse(String code, String message) {
        super(code, message);
    }

    public ErrorResponse(String code) {
        super(code);
    }
}
