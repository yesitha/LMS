package com.itgura.util;

public class BootMain {
	public static void main(String[] args) {
		//System.setProperty("spring.profiles.active", "stg");
		System.setProperty("spring.config.name", ApplicationConfig.APP_CONFING_NAME);
	}
}
