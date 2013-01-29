package org.springframework.social.linkedin.security;

import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.security.provider.OAuth1AuthenticationService;

public class LinkedInAuthenticationService extends OAuth1AuthenticationService<LinkedIn> {

	public LinkedInAuthenticationService(String apiKey, String appSecret) {
		super(new LinkedInConnectionFactory(apiKey, appSecret));
	}

}
