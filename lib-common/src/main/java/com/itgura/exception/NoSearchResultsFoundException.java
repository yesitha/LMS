package com.itgura.exception;

public class NoSearchResultsFoundException extends ApplicationException{
    private static final long serialVersionUID = 1L;

    public NoSearchResultsFoundException() {
        super();
    }

    public NoSearchResultsFoundException(String message) {
        super(message);
    }

    public NoSearchResultsFoundException(String message, Throwable e) {
        super(message, e);
    }
}
