/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.connect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * LinkedIn ServiceProvider implementation.
 * @author Keith Donald
 */
public class LinkedInServiceProvider extends AbstractOAuth2ServiceProvider<LinkedIn> {
	public static final String DEFAULT_SCOPE = "r_basicprofile";
	String state;
	String scope;
	@Autowired(required=false)
	private ConnectController connectController;

	public LinkedInServiceProvider(String clientId, String clientSecret,String state) {
		this(clientId,clientSecret,state,DEFAULT_SCOPE);
	}
	
	public LinkedInServiceProvider(String clientId, String clientSecret,
			String state, String scope) {
		super(getOAuth2Template(clientId, clientSecret));
		this.state = state;
		this.scope = scope;
		if(connectController != null) {
			connectController.addInterceptor(new LinkedinConnectInterceptor(state, scope));
		}
	}


	private static OAuth2Template getOAuth2Template(String clientId, String clientSecret) {
		OAuth2Template oAuth2Template = new OAuth2Template(clientId, clientSecret,
			"https://www.linkedin.com/uas/oauth2/authorization",
			"https://www.linkedin.com/uas/oauth2/accessToken");
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	public LinkedIn getApi(String accessToken) {
		return new LinkedInTemplate(accessToken);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
	
}
