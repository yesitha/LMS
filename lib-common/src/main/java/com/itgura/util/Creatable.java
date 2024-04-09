package com.itgura.util;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.NullNotAllowedException;
import com.itgura.exception.ValueNotExistException;

public interface Creatable<AppRequest, AppResponse> {	
	public AppResponse create(AppRequest req) throws ApplicationException, NullNotAllowedException, ValueNotExistException;
}
