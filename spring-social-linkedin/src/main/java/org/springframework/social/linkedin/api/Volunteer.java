package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Volunteer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String organization;
	
	private final String role;

	public Volunteer(String organization, String role) {
		this.organization = organization;
		this.role = role;
	}

	public String getOrganization() {
		return organization;
	}

	public String getRole() {
		return role;
	}
	
}
