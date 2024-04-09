package com.itgura.util;

import com.itgura.exception.ApplicationException;

public interface Deletable {	
	public void delete(Long id) throws ApplicationException;
}
