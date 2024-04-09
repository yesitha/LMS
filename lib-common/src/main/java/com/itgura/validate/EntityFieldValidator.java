package com.itgura.validate;

import com.itgura.config.EntityFieldValidatorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class EntityFieldValidator {

	@Autowired
	private EntityFieldValidatorConfig entityFieldValidatorConfig;

	private EntityFieldValidator() {
		super();
	}

	public String getNotExist(String property, String ownObject, String... value) {
		return MessageFormat.format(entityFieldValidatorConfig.getNotExist(), property, ownObject);
	}
	
	public String getCreateFail(String object) {
		return MessageFormat.format(entityFieldValidatorConfig.getCreateFail(), object);
	}
	
	public String getUpdateFail(String property, String ownObject) {
		return MessageFormat.format(entityFieldValidatorConfig.getUpdateFail(), property, ownObject);
	}

	public String getCannotBeNull(String property, String ownObject) {
		return MessageFormat.format(entityFieldValidatorConfig.getCannotBeNull(), property, ownObject);
	}
	
	public String getFail(String property, String ownObject) {
		return MessageFormat.format(entityFieldValidatorConfig.getCannotBeNull(), property, ownObject);
	}
}
