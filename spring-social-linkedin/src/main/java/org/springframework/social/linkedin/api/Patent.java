package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Patent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String title;
	
	private final LinkedInDate date;

	public Patent(String title, LinkedInDate date) {
		this.title = title;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public LinkedInDate getDate() {
		return date;
	}
	
}
