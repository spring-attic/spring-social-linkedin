/*
 * Copyright 2014 the original author or authors.
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
package org.springframework.social.linkedin.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;

public class ErrorHandlerTest extends AbstractLinkedInApiTest {

	@Test(expected=IllegalArgumentException.class)
	public void nullAccessToken() throws Exception {
		new LinkedInTemplate(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void emptyAccessToken() throws Exception {
		new LinkedInTemplate("");
	}

	@Test
	public void invalidToken() throws Exception {
		try {
			mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~:(id,first-name,last-name,emailAddress,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json&oauth2_access_token=ACCESS_TOKEN"))
				.andExpect(method(GET))
				.andRespond(withStatus(HttpStatus.UNAUTHORIZED).body(jsonResource("error_invalid_token")).contentType(MediaType.APPLICATION_JSON));
			linkedIn.profileOperations().getUserProfile();
			fail();
		} catch (NotAuthorizedException e) {
			assertEquals("Invalid access token.", e.getMessage());
		}
	}

	@Test
	public void throttle() throws Exception {
		try {
			mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~:(id,first-name,last-name,emailAddress,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json&oauth2_access_token=ACCESS_TOKEN"))
				.andExpect(method(GET))
				.andRespond(withStatus(HttpStatus.FORBIDDEN).body(jsonResource("error_throttle")).contentType(MediaType.APPLICATION_JSON));
			linkedIn.profileOperations().getUserProfile();
			fail();
		} catch (RateLimitExceededException e) {
			assertEquals("The rate limit has been exceeded.", e.getMessage());
		}
	}

	@Test
	public void insufficientPermission() throws Exception {
		try {
			mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~:(id,first-name,last-name,emailAddress,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json&oauth2_access_token=ACCESS_TOKEN"))
				.andExpect(method(GET))
				.andRespond(withStatus(HttpStatus.FORBIDDEN).body(jsonResource("error_insufficient_permission")).contentType(MediaType.APPLICATION_JSON));
			linkedIn.profileOperations().getUserProfile();
			fail();
		} catch (InsufficientPermissionException e) {
			assertEquals("Insufficient permission for this operation.", e.getMessage());
		}
	}

	@Test
	public void notFound() throws Exception {
		try {
			mockServer.expect(requestTo("https://api.linkedin.com/v1/people/id=1234:(id,first-name,last-name,emailAddress,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json&oauth2_access_token=ACCESS_TOKEN"))
				.andExpect(method(GET))
				.andRespond(withStatus(HttpStatus.NOT_FOUND).body(jsonResource("error_not_found")).contentType(MediaType.APPLICATION_JSON));
			linkedIn.profileOperations().getProfileById("1234");
			fail();
		} catch (ResourceNotFoundException e) {
			assertEquals("Invalid member id {1234}", e.getMessage());
		}
	}

}
