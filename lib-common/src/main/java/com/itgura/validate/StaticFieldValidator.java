package com.itgura.validate;

import com.itgura.config.StaticFieldValidatorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaticFieldValidator {

	@Autowired
	private StaticFieldValidatorConfig staticFieldValidatorConfig;

	private StaticFieldValidator() {
		super();
	}

// Validate NIC Number
	public boolean validateNIC(String nic) {		
		return ((nic.isEmpty()) ? false : nic.matches(staticFieldValidatorConfig.getNicPattern())); //
	}
}
