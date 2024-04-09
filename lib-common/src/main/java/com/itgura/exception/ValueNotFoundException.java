package com.itgura.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public ValueNotFoundException() {
        super();
    }

    public ValueNotFoundException(String message) {
        super(message);
    }

    public ValueNotFoundException(String message, Throwable e) {
        super(message, e);
    }

}
