package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final String name;
	
	public Company(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
