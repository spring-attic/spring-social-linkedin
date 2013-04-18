package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	
	private final String number;

	public Course(String name, String number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}
	
}
