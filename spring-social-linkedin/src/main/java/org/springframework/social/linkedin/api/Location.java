package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String country;
	private final String name;

	public Location(String country, String name) {
		this.country = country;
		this.name = name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getName() {
		return name;
	}
}
