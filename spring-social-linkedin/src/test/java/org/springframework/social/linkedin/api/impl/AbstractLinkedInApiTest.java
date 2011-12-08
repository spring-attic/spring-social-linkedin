package org.springframework.social.linkedin.api.impl;

import static org.junit.Assert.assertEquals;

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
import org.springframework.social.test.client.MockRestServiceServer;

public class AbstractLinkedInApiTest {
	protected LinkedInTemplate linkedIn;
	protected MockRestServiceServer mockServer;
	protected HttpHeaders responseHeaders;
	
	@Before
	public void setup() {
		linkedIn = new LinkedInTemplate("API_KEY", "API_SECRET", "ACCESS_TOKEN", "ACCESS_TOKEN_SECRET");
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
//		assertEquals(standardUrl, connection.getStandardProfileUrl());
	}
}
