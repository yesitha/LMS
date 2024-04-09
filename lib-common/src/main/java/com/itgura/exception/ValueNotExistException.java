package com.itgura.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ValueNotExistException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public ValueNotExistException() {
		super();
	}

	public ValueNotExistException(String message) {
		super(message);
	}

	public ValueNotExistException(String message, Throwable e) {
		super(message, e);
	}

}
