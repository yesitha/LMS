package com.itgura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Request {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public String toString() {
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			//getErrorlogger().error(e);
		}
		return "{}";
	}
	
	
	
	
}