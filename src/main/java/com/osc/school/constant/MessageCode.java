package com.osc.school.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageCode {
    private String code;
    private String message;

    public MessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MessageCode(String code) {
        this(code,"");
    }
}
