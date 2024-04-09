package com.itgura.util;

//import org.springframework.data.domain.PageImpl;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.NullNotAllowedException;
import com.itgura.exception.ValueNotExistException;

public interface Searchable<AppRequest, AppResponse> {
	
	default  AppResponse search(AppRequest req) throws ApplicationException, NullNotAllowedException, ValueNotExistException {
		return null;
	}
	
	default AppResponse search() throws ApplicationException, NullNotAllowedException, ValueNotExistException {
		return null;
	}
	
	default AppResponse search(AppRequest req, Integer pageNumber, Integer pageSize) throws ApplicationException, NullNotAllowedException, ValueNotExistException {
		return null;
	}
//	default PageImpl<AppResponse> searchWithPagination(AppRequest req, int page, int size) throws ApplicationException, NullNotAllowedException, ValueNotExistException {
//		return null;
//	}

}
