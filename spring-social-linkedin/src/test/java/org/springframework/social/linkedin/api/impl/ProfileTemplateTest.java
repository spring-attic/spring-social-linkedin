/*
 * Copyright 2010 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.linkedin.api.LinkedInProfile;

/**
 * @author Craig Walls
 */
public class ProfileTemplateTest extends AbstractLinkedInApiTest {

	@Test
	public void getUserProfile() {
		mockServer.expect(requestTo(ProfileTemplate.PROFILE_URL)).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("testdata/profile.json", getClass()), responseHeaders));
		LinkedInProfile profile = linkedIn.profileOperations().getUserProfile();
		assertEquals("z37f0n3A05", profile.getId());
		assertEquals("Just a guy", profile.getHeadline());
		assertEquals("Craig", profile.getFirstName());
		assertEquals("Walls", profile.getLastName());
		assertEquals("Computer Software", profile.getIndustry());
		assertEquals("http://www.linkedin.com/in/habuma", profile.getPublicProfileUrl());
		assertEquals("http://www.linkedin.com/standardProfileUrl", profile.getStandardProfileUrl());
		assertEquals("http://media.linkedin.com/pictureUrl", profile.getProfilePictureUrl());
	}

	@Test
	public void getProfileId() {
		mockServer.expect(requestTo(ProfileTemplate.PROFILE_URL)).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("testdata/profile.json", getClass()), responseHeaders));
		assertEquals("z37f0n3A05", linkedIn.profileOperations().getProfileId());
	}

	@Test
	public void getProfileUrl() {
		mockServer.expect(requestTo(ProfileTemplate.PROFILE_URL)).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("testdata/profile.json", getClass()), responseHeaders));
		assertEquals("http://www.linkedin.com/in/habuma", linkedIn.profileOperations().getProfileUrl());
	}
}
