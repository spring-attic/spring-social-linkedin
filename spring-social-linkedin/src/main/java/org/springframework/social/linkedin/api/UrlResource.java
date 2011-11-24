package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Url Resource
 * 
 * @author Robert Drysdale
 *
 */
public class UrlResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final String url;
	
	public UrlResource(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
}
