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
package org.springframework.social.linkedin.api.impl;

import static org.hamcrest.core.StringContains.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import java.util.Date;

import org.junit.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.LinkedInDate;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.MemberGroup;
import org.springframework.social.linkedin.api.PersonActivity;
import org.springframework.social.linkedin.api.Recommendation;
import org.springframework.social.linkedin.api.Recommendation.RecommendationType;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;

public class AbstractLinkedInApiTest {
	protected LinkedInTemplate linkedIn;
	protected MockRestServiceServer mockServer;
	protected HttpHeaders responseHeaders;
	
	@Before
	public void setup() {
		linkedIn = new LinkedInTemplate("ACCESS_TOKEN");
		mockServer = MockRestServiceServer.createServer(linkedIn.getRestTemplate());
		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	}
	
	protected void assertPersonActivity(PersonActivity activity, Integer id, String body) {
		assertEquals((int)id, activity.getAppId());
		assertEquals(body, activity.getBody());
	}
	
	protected void assertRecommendation(Recommendation recommendation, String snippet, RecommendationType recommendationType) {
		assertEquals(recommendationType, recommendation.getRecommendationType());
		assertEquals(snippet, recommendation.getRecommendationSnippet());
	}
	
	protected void assertGroup(MemberGroup group, String id, String name, String url) {
		assertEquals(id, group.getId());
		assertEquals(name, group.getName());
		assertEquals(url, group.getSiteGroupRequest().getUrl());
	}
	
	protected void assertShare(CurrentShare share, String id, String visibility, String serviceProvider, String accountHandle, String accountId, String shareId, String comment) {
		assertEquals(id, share.getId());
		assertEquals(serviceProvider, share.getSource().getServiceProvider());
		assertEquals(accountHandle, share.getSource().getServiceProviderAccountHandle());
		assertEquals(accountId, share.getSource().getServiceProviderAccountId());
		assertEquals(shareId, share.getSource().getServiceProviderShareId());
		assertEquals(comment, share.getComment());
		assertEquals(visibility, share.getVisibility());
	}
	
	protected void assertUpdate(LinkedInNetworkUpdate update, UpdateType type, Class<? extends UpdateContent> updateClass, Date date, String updateKey) {
		assertEquals(type, update.getUpdateType()) ;
		assertEquals(updateClass, update.getUpdateContent().getClass());
		assertEquals(date, update.getTimestamp());
		assertEquals(updateKey, update.getUpdateKey());
	}
	
	protected void assertLinkedInDate(LinkedInDate date, int year, int month, int day) {
		assertEquals(year, date.getYear());
		assertEquals(month, date.getMonth());
		assertEquals(day, date.getDay());
	}

	protected void assertProfile(LinkedInProfile connection, String id, String headline, String firstName,
			String lastName, String industry, String standardUrl) {
		assertEquals(id, connection.getId());
		assertEquals(headline, connection.getHeadline());
		assertEquals(firstName, connection.getFirstName());
		assertEquals(lastName, connection.getLastName());
		assertEquals(industry, connection.getIndustry());
	}
	

	@SuppressWarnings("unchecked")
	protected RequestMatcher headerContains(String name, String substring) {
		return header(name, containsString(substring));
	}

}
