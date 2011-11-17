package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class TwitterAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String providerAccountId;
	private final String providerAccountName;

	public TwitterAccount(String providerAccountId, String providerAccountName) {
		this.providerAccountId = providerAccountId;
		this.providerAccountName = providerAccountName;
	}
	
	public String getProviderAccountId() {
		return providerAccountId;
	}
	
	public String getProviderAccountName() {
		return providerAccountName;
	}
}
