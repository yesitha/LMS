package com.itgura.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:static-field-validation.properties")
@Setter
@Getter
public class StaticFieldValidatorConfig {
	
	@Value("${regex.patten.nic}")
	private String nicPattern;
	
	@Value("${regex.patten.email}")
	private String emailPattern;
	
	@Value("${regex.patten.mobile.number}")
	private String mobileNumberPattern;

	
}

