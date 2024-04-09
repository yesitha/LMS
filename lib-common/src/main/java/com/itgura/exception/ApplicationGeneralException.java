package com.itgura.exception;


public class ApplicationGeneralException extends Exception {

	private static final long serialVersionUID = 1L;

	public ApplicationGeneralException() {
		super();
	}

	public ApplicationGeneralException(String message) {
		super(message);
	}

	public ApplicationGeneralException(String message, Throwable e) {
		super(message, e);
	}

    public ApplicationGeneralException(Throwable cause) {
        super(cause);
    }
}
