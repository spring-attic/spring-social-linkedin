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

import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * LinkedIn ServiceProvider implementation.
 * @author Keith Donald
 */
public class LinkedInServiceProvider extends AbstractOAuth2ServiceProvider<LinkedIn> {

	public LinkedInServiceProvider(String clientId, String clientSecret) {
		super(new OAuth2Template(clientId, clientSecret,
			"https://www.linkedin.com/uas/oauth2/authorization",
			"https://www.linkedin.com/uas/oauth2/accessToken"));
	}

	public LinkedIn getApi(String accessToken) {
		return new LinkedInTemplate(accessToken);
	}
	
}
