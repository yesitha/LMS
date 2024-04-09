package com.itgura.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NullNotAllowedException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public NullNotAllowedException() {
		super();
	}

	public NullNotAllowedException(String message) {
		super(message);
	}

	public NullNotAllowedException(String message, Throwable e) {
		super(message, e);
	}

}
