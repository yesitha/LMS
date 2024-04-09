package com.itgura.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:entity-field-validation.properties")
@Setter
@Getter
public class EntityFieldValidatorConfig {
	
	public static final String ID_REQUIRED_MSG = "id cannot be missing or empty";
	public static final String PAGENUMBER_OPTIONAL_MSG = "page-number is optional, default is Integer.MAX_VALUE";
	public static final String PAGESIZE_OPTIONAL_MSG = "page-size is optional, default is zero(0)";
	public static final String CODE_REQUIRED_MSG = "code cannot be missing or empty";
	
	@Value("${error.not_exist}")
	private String notExist;

	@Value("${error.create_fail}")
	private String createFail;

	@Value("${error.update_fail}")
	private String updateFail;

	@Value("${error.cannot_be_null}")
	private String cannotBeNull;
	
	
}

