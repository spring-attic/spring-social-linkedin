package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class CodeAndName implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String code;
	private final String name;
	
	public CodeAndName(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
