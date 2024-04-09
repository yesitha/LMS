package com.itgura.util;


import com.itgura.exception.ApplicationException;
import com.itgura.exception.NullNotAllowedException;
import com.itgura.exception.ValueNotExistException;

import java.util.UUID;

public interface RecordReadable <AppResponse, AppResponseDetail> {	
	
	default AppResponse readById(UUID id) throws NullNotAllowedException, ValueNotExistException, ApplicationException {
		return null;
	}
	
	default AppResponse readById(Long id, Integer pageNumber, Integer pageSize) throws NullNotAllowedException, ValueNotExistException, ApplicationException {
		return null;
	}

	default AppResponse readByRefNo(Long id, Integer pageNumber, Integer pageSize) throws NullNotAllowedException, ValueNotExistException, ApplicationException {
		return null;
	}

	default AppResponse readByRefNo(Long refNo) throws NullNotAllowedException, ValueNotExistException, ApplicationException {
		return null;
	}
	
	default AppResponse readByCode(String code) throws NullNotAllowedException, ValueNotExistException, ApplicationException {
		return null;
	}
	
	default AppResponseDetail readAll(Integer pageNumber, Integer pageSize) throws ApplicationException {
		return null;
	}
	
	
}
