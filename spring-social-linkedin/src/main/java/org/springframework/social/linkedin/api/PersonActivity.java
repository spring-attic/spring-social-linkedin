package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class PersonActivity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int appId;
	private final String body;
	
	public PersonActivity(int appId, String body) {
		this.appId = appId;
		this.body = body;
	}
	
	public int getAppId() {
		return appId;
	}
	
	public String getBody() {
		return body;
	}
}
