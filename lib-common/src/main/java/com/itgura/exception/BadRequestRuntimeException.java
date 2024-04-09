package com.itgura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestRuntimeException extends ApplicationGeneralException {
    /**
	 *
	 */
	private static final long serialVersionUID = -5021377332677132504L;

	public BadRequestRuntimeException() {
        super();
    }

    public BadRequestRuntimeException(String message) {
        super(message);
    }

    public BadRequestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestRuntimeException(Throwable cause) {
        super(cause);
    }
}
