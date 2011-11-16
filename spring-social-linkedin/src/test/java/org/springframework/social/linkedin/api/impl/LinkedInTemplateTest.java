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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.social.test.client.RequestMatchers.body;
import static org.springframework.social.test.client.RequestMatchers.headerContains;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.MemberGroup;
import org.springframework.social.linkedin.api.PersonActivity;
import org.springframework.social.linkedin.api.Recommendation;
import org.springframework.social.linkedin.api.NewShare.NewShareVisibility;
import org.springframework.social.linkedin.api.NewShare.NewShareVisibilityCode;
import org.springframework.social.linkedin.api.Recommendation.RecommendationType;
import org.springframework.social.linkedin.api.NewShare;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentConnection;
import org.springframework.social.linkedin.api.UpdateContentGroup;
import org.springframework.social.linkedin.api.UpdateContentPersonActivity;
import org.springframework.social.linkedin.api.UpdateContentRecommendation;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateContentViral;
import org.springframework.social.linkedin.api.UpdateType;
import org.springframework.social.test.client.MockRestServiceServer;

/**
 * @author Craig Walls
 */
public class LinkedInTemplateTest {

	private LinkedInTemplate linkedIn;
	private MockRestServiceServer mockServer;
	private HttpHeaders responseHeaders;

	@Before
	public void setup() {
		linkedIn = new LinkedInTemplate("API_KEY", "API_SECRET", "ACCESS_TOKEN", "ACCESS_TOKEN_SECRET");
		mockServer = MockRestServiceServer.createServer(linkedIn.getRestTemplate());
		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void getUserProfile() {
		mockServer.expect(requestTo(LinkedInTemplate.PROFILE_URL)).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("profile.json", getClass()), responseHeaders));
		LinkedInProfile profile = linkedIn.getUserProfile();
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
		mockServer.expect(requestTo(LinkedInTemplate.PROFILE_URL)).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("profile.json", getClass()), responseHeaders));
		assertEquals("z37f0n3A05", linkedIn.getProfileId());
	}

	@Test
	public void getProfileUrl() {
		mockServer.expect(requestTo(LinkedInTemplate.PROFILE_URL)).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("profile.json", getClass()), responseHeaders));
		assertEquals("http://www.linkedin.com/in/habuma", linkedIn.getProfileUrl());
	}

	@Test
	public void getConnections() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/connections?format=json")).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("connections.json", getClass()), responseHeaders));
		List<LinkedInProfile> connections = linkedIn.getConnections();
		assertEquals(4, connections.size());
		assertProfile(connections.get(0), "kR0lnX1ll8", "SpringSource Cofounder", "Keith", "Donald", "Computer Software",
				"http://www.linkedin.com/profile?viewProfile=&key=2526541&authToken=61Sm&authType=name&trk=api*a121026*s129482*");
		assertProfile(connections.get(1), "VRcwcqPCtP", "GM, SpringSource and SVP, Middleware at VMware", "Rod",
				"Johnson",
				"Computer Software",
				"http://www.linkedin.com/profile?viewProfile=&key=210059&authToken=3hU1&authType=name&trk=api*a121026*s129482*");
		assertProfile(connections.get(2), "Ia7uR1OmDB", "Spring and AOP expert; author AspectJ in Action", "Ramnivas",
				"Laddad", "Computer Software",
				"http://www.linkedin.com/profile?viewProfile=&key=208994&authToken=P5K9&authType=name&trk=api*a121026*s129482*");
		assertProfile(connections.get(3), "gKEMq4CMdl", "Head of Groovy Development at SpringSource", "Guillaume",
				"Laforge", "Information Technology and Services",
				"http://www.linkedin.com/profile?viewProfile=&key=822306&authToken=YmIW&authType=name&trk=api*a121026*s129482*");
	}
	
	@Test 
	public void postUpdate() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/person-activities"))
		.andExpect(method(PUT))
		.andExpect(body("{\"body\":\"Cool beans\",\"contentType\":\"linkedin-html\"}"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		linkedIn.createNetworkUpdate("Cool beans");
	}
	
	@Test
	public void share() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/shares"))
		.andExpect(method(POST))
		.andExpect(body("{\"comment\":\"Social Integration Platform coming together nicely ...\",\"visibility\":{\"code\":\"connections-only\"}}"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		NewShare share = new NewShare();
		share.setComment("Social Integration Platform coming together nicely ...");
		share.setVisibility(new NewShareVisibility(NewShareVisibilityCode.CONNECTIONS_ONLY));
		
		linkedIn.share(share);
	}
	
	@Test
	public void like() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates/key=KEY/is-liked?format=json"))
		.andExpect(method(PUT))
		.andExpect(body("true"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		
		linkedIn.likeNetworkUpdate("KEY");
	}
	
	@Test
	public void unlike() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates/key=KEY/is-liked?format=json"))
		.andExpect(method(PUT))
		.andExpect(body("false"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		
		linkedIn.unlikeNetworkUpdate("KEY");
	}
	
	@Test
	public void getCurrentShare() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~:(current-share)"))
		.andRespond(withResponse(new ClassPathResource("current.json", getClass()), responseHeaders));
		
		CurrentShare share = linkedIn.getCurrentShare();
		assertShare(share, "s702970589", "connections-only", "LINKEDIN", null, null, null, "It's not sexy but ...");
		assertProfile(share.getAuthor(), "UB2kruYmAL", null, "Robert", "Drysdale", null, null);
	}
	
	@Test 
	public void getUpdates() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates?format=json&count=400&start=0&type=ANSW&type=APPS&type=CMPY&type=CONN&type=JOBS&type=JGRP&type=PICT&type=PRFX&type=RECU&type=PRFU&type=QSTN&type=SHAR&type=VIRL")).andExpect(method(GET))
		.andRespond(withResponse(new ClassPathResource("updates.json", getClass()), responseHeaders));
		List<LinkedInNetworkUpdate> updates = linkedIn.getNetworkUpdates();
		assertEquals(9, updates.size());
		
		// Connection Update
		assertUpdate(updates.get(0), UpdateType.CONN, UpdateContentConnection.class,
				new Date(1321282506000l), "CONN-6870400-*2-*1");
		assertProfile(updates.get(0).getUpdateContent(), "L95Bmv9vvv", "CEM Consultant at Smith", "Nicolas", "Smith", null, "");
		assertProfile( ((UpdateContentConnection)updates.get(0).getUpdateContent()).getConnections().get(0),
				"x3AmSzDvvv", "Director at Smith", "Sanjay", "Smith", null, "");
		
		// Share Update
		assertUpdate(updates.get(1), UpdateType.SHAR, UpdateContentShare.class,
				new Date(1321280958582l), "UNIU-2481200-5541854009700100000-SHARE");
		assertProfile(updates.get(1).getUpdateContent(), "r90Z7yavvv", "VP Sales & Operations at Smith", "Niall", "Smith", null, "");
		assertShare(((UpdateContentShare)updates.get(1).getUpdateContent()).getCurrentShare(), 
				"s699246000", "anyone", "TWITTER", "niall_smith", "8427000", "136088316402614000",
				"Take a photo tour of what the new Kindle Fire has to offer. http://t.co/cEXESrIv via @CNET #li");
		assertProfile(((UpdateContentShare)updates.get(1).getUpdateContent()).getCurrentShare().getAuthor(), "r90Z7yavvv", "VP Sales & Operations at Smith", "Niall", "Smith", null, "");
		
		// Profile Update
		assertUpdate(updates.get(2), UpdateType.PROF, UpdateContent.class,
				new Date(1321271141533l), "PROF-78067750-5541812856741100000-*1");
		assertArrayEquals(updates.get(2).getUpdatedFields().toArray(), new String[] {"person/headline", "person/specialties", "person/positions"});
		
		// Group Update
		assertUpdate(updates.get(3), UpdateType.JGRP, UpdateContentGroup.class,
				new Date(1321271102476l), "JGRP-2481200-5541812670493100000-*1");
		assertGroup(((UpdateContentGroup)updates.get(3).getUpdateContent()).getMemberGroups().get(0), "130889", "Irish Executives", "http://www.linkedin.com/groups?gid=130889");
		
		// Viral Update (Contains Share Update embedded)
		assertUpdate(updates.get(4), UpdateType.VIRL, UpdateContentViral.class,
				new Date(1321271057014l), "UNIU-28432557-5541812479593500000-VIRAL");
		assertShare(((UpdateContentShare)((UpdateContentViral)updates.get(4).getUpdateContent()).getUpdateAction().getUpdateContent()).getCurrentShare(),
				"s698865000", "anyone", "LINKEDIN", null, null, null, 
				"The NOW Factory ISA Company of the Year 2011");
		
		// Profile Picture Update
		assertUpdate(updates.get(5), UpdateType.PICU, UpdateContent.class,
				new Date(1321269958918l), "PICU-78067750-5541807874285500000-*1");
		assertProfile(updates.get(5).getUpdateContent(), "B5Set8lvvv", "Enterprise Officer at Smith", "john", "Smith", null, "");
		
		// Recommended Update
		assertUpdate(updates.get(6), UpdateType.PREC, UpdateContentRecommendation.class,
				new Date(1320771064517l), "PREC-25073976-5539715374557100000-*1");
		assertRecommendation(((UpdateContentRecommendation)updates.get(6).getUpdateContent()).getRecommendationsReceived().get(0),
				"Over the past 5 years, I have been fortunate enough to work with John and his team at Smith acros...", RecommendationType.BUSINESS_PARTNER);
		assertProfile(((UpdateContentRecommendation)updates.get(6).getUpdateContent()).getRecommendationsReceived().get(0).getRecommender(), 
				"Zo4xt_cvvv", "Internet Business Developer and SEO", "David", "Smith", null, "");
		
		// Recommend Update
		assertUpdate(updates.get(7), UpdateType.PREC, UpdateContentRecommendation.class,
				new Date(1321307620783l), "PREC-7024000-5541965839374225000-*1");
		assertRecommendation(((UpdateContentRecommendation)updates.get(7).getUpdateContent()).getRecommendationsGiven().get(0),
				"I have worked with Chris on a number of projects in Smith.  In all of that time ...", RecommendationType.COLLEAGUE);
		assertProfile(((UpdateContentRecommendation)updates.get(7).getUpdateContent()).getRecommendationsGiven().get(0).getRecommendee(), 
				"2nnDBUHvvv", "Information Technology and Services Professional", "Chris", "Smith", null, "");
		
		// Activity Update
		assertUpdate(updates.get(8), UpdateType.APPM, UpdateContentPersonActivity.class,
				new Date(1321285689160l), "APPM-7024701-554187385169820000-1700");
		assertPersonActivity(((UpdateContentPersonActivity)updates.get(8).getUpdateContent()).getPersonActivities().get(0),
				1700, "<a href=\"http://www.linkedin.com//profile?viewProfile=&key=7024000\">Paul O&#39;Smith</a> recommends <a href=\"http://www.linkedin.com//redirect?url=http%3A%2F%2Fwww%2Elinkedin%2Ecom%2Fosview%2Fcanvas%3F_ch_page_id%3D1%26_ch_panel_id%3D3%26_ch_app_id%3D20%26_applicationId%3D1700%26_ownerId%3D7024701%26osUrlHash%3DepGa%26appParams%3D%257B%2522view%2522%253A%2522book%2522%252C%2522asin%2522%253A%25220446563048%2522%252C%2522offset%2522%253A%25220%2522%257D&urlhash=aA19\">Delivering Happiness: A Path to Profits, Passion, and Purpose</a>");
	}
	
	private void assertPersonActivity(PersonActivity activity, Integer id, String body) {
		assertEquals((Integer)id, (Integer)activity.getAppId());
		assertEquals(body, activity.getBody());
	}
	
	private void assertRecommendation(Recommendation recommendation, String snippet, RecommendationType recommendationType) {
		assertEquals(recommendationType, recommendation.getRecommendationType());
		assertEquals(snippet, recommendation.getRecommendationSnippet());
	}
	
	private void assertGroup(MemberGroup group, String id, String name, String url) {
		assertEquals(id, group.getId());
		assertEquals(name, group.getName());
		assertEquals(url, group.getUrl());
	}
	
	private void assertShare(CurrentShare share, String id, String visibility, String serviceProvider, String accountHandle, String accountId, String shareId, String comment) {
		assertEquals(id, share.getId());
		assertEquals(serviceProvider, share.getSource().getServiceProvider());
		assertEquals(accountHandle, share.getSource().getServiceProviderAccountHandle());
		assertEquals(accountId, share.getSource().getServiceProviderAccountId());
		assertEquals(shareId, share.getSource().getServiceProviderShareId());
		assertEquals(comment, share.getComment());
		assertEquals(visibility, share.getVisibility());
	}
	
	private void assertUpdate(LinkedInNetworkUpdate update, UpdateType type, Class<? extends UpdateContent> updateClass, Date date, String updateKey) {
		assertEquals(type, update.getUpdateType()) ;
		assertEquals(updateClass, update.getUpdateContent().getClass());
		assertEquals(date, update.getTimestamp());
		assertEquals(updateKey, update.getUpdateKey());
	}

	private void assertProfile(LinkedInProfile connection, String id, String headline, String firstName,
			String lastName, String industry, String standardUrl) {
		assertEquals(id, connection.getId());
		assertEquals(headline, connection.getHeadline());
		assertEquals(firstName, connection.getFirstName());
		assertEquals(lastName, connection.getLastName());
		assertEquals(industry, connection.getIndustry());
//		assertEquals(standardUrl, connection.getStandardProfileUrl());
	}

}
