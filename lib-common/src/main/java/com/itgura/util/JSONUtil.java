package com.itgura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itgura.dto.AppRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JSONUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JSONUtil.class);

	private JSONUtil() {

	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String toString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		return "{}";
	}

	public static synchronized  <T> T get(String fileName, Class<T> type) {
		TypeReference<T> typeReference = new TypeReference<T>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream(fileName);
			try {
				Object returnObj = objectMapper.readValue(inputStream, typeReference);
				return objectMapper.convertValue(returnObj, type);
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
			return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static synchronized  <T> AppRequest<T> get(String fileName) {
		TypeReference<T> typeReference = new TypeReference<T>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream(fileName);
			try {
				Object returnObj = objectMapper.readValue(inputStream, typeReference);
				return objectMapper.convertValue(returnObj, AppRequest.class);
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
			return null;
	}
	
	
	public static <T> T getObject(String object, Class<T> type) {
		try {
			return objectMapper.readValue(object, type);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

}
