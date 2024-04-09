package com.itgura.util;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.NullNotAllowedException;
import com.itgura.exception.ValueNotExistException;

import java.util.UUID;

public interface Updatable<AppRequest, AppResponse> {	
	public AppResponse update(AppRequest req, UUID id) throws ApplicationException, NullNotAllowedException, ValueNotExistException;
}
