package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class MemberGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final String name;
	private final String url;
	
	public MemberGroup(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
}
