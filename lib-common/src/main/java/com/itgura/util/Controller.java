package com.itgura.util;

import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ApplicationGeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

public interface Controller {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	default AppResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		String e = JSONUtil.toString(errors);
		return AppResponse.error(null, errors.values().toString(), HttpStatus.BAD_REQUEST.toString(), null, "One or more invalid arguments found");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	default AppResponse<Object> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String name = ex.getName();
	    String type = ex.getRequiredType().getSimpleName();
	    Object value = ex.getValue();
	    String message = String.format("'%s' parameter in the request should be of data type '%s' and the value specified '%s' is not of invalid data type",
	                                   name, type, value);
	    return AppResponse.error(null, HttpStatus.BAD_REQUEST.toString(), "Argument Type Mismatch", null, message);
	}

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ApplicationException.class)
	default AppResponse<Object> handleApplicationException(ApplicationException e) {
		return AppResponse.error(null, HttpStatus.UNPROCESSABLE_ENTITY.toString(), null, null, e.getMessage());

	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApplicationGeneralException.class)
	default AppResponse<Object> handleApplicationGeneralException(ApplicationGeneralException e) {
		return AppResponse.error(null, HttpStatus.INTERNAL_SERVER_ERROR.toString(), null, null, e.getMessage());
	}


    default String getClassName(ApplicationException e) {
		return e.getStackTrace()[0].getClassName();
	}


    default String getMethodName(ApplicationException e) {
		return e.getStackTrace()[0].getMethodName();
	}
	public default String getClassName() {
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		return stackTrace[0].getClassName();
	}

	public default String getMethodName() {
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		return stackTrace[1].getMethodName();
	}
}