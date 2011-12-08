package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class ApiStandardProfileRequest implements Serializable {

	private static final long serialVersionUID = -4957171160371820101L;
	
	private final String name;
	private final String value;
	
	public ApiStandardProfileRequest(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

}
