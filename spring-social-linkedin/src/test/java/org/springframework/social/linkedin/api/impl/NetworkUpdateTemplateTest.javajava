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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.NewShare;
import org.springframework.social.linkedin.api.NewShare.NewShareVisibility;
import org.springframework.social.linkedin.api.NewShare.NewShareVisibilityCode;
import org.springframework.social.linkedin.api.Recommendation.RecommendationType;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentConnection;
import org.springframework.social.linkedin.api.UpdateContentGroup;
import org.springframework.social.linkedin.api.UpdateContentPersonActivity;
import org.springframework.social.linkedin.api.UpdateContentRecommendation;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateContentViral;
import org.springframework.social.linkedin.api.UpdateType;

public class NetworkUpdateTemplateTest extends AbstractLinkedInApiTest {

	@Test 
	public void postUpdate() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/person-activities?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("{\"body\":\"Cool beans\",\"contentType\":\"linkedin-html\"}"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		linkedIn.networkUpdateOperations().createNetworkUpdate("Cool beans");
	}
	
	@Test
	public void share() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/shares?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("{\"comment\":\"Social Integration Platform coming together nicely ...\",\"visibility\":{\"code\":\"connections-only\"}}"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		NewShare share = new NewShare();
		share.setComment("Social Integration Platform coming together nicely ...");
		share.setVisibility(new NewShareVisibility(NewShareVisibilityCode.CONNECTIONS_ONLY));
		
		linkedIn.networkUpdateOperations().share(share);
	}
	
	@Test
	public void like() {		
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates/key=KEY/is-liked?format=json&oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(PUT))
			.andExpect(content().string("true"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		linkedIn.networkUpdateOperations().likeNetworkUpdate("KEY");
	}
	
	@Test
	public void unlike() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates/key=KEY/is-liked?format=json&oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(PUT))
			.andExpect(content().string("false"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		linkedIn.networkUpdateOperations().unlikeNetworkUpdate("KEY");
	}
	
	@Test
	public void getCurrentShare() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~:(current-share)?oauth2_access_token=ACCESS_TOKEN"))
			.andRespond(withSuccess(new ClassPathResource("current.json", getClass()), MediaType.APPLICATION_JSON));
		
		CurrentShare share = linkedIn.networkUpdateOperations().getCurrentShare();
		assertShare(share, "s702970589", "connections-only", "LINKEDIN", null, null, null, "It's not sexy but ...");
		assertProfile(share.getAuthor(), "UB2kruYmAL", null, "Robert", "Drysdale", null, null);
	}
	
	@Test 
	public void getUpdates() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates?count=10&start=0&type=ANSW&type=APPS&type=CMPY&type=CONN&type=JOBS&type=JGRP&type=PICT&type=PRFX&type=RECU&type=PRFU&type=QSTN&type=SHAR&type=VIRL&format=json&oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("updates.json", getClass()), MediaType.APPLICATION_JSON));
		List<LinkedInNetworkUpdate> updates = linkedIn.networkUpdateOperations().getNetworkUpdates();
		assertUpdates(updates);
	}
	
	@Test 
	public void getUpdates_withStartAndCount() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/updates?count=30&start=10&type=ANSW&type=APPS&type=CMPY&type=CONN&type=JOBS&type=JGRP&type=PICT&type=PRFX&type=RECU&type=PRFU&type=QSTN&type=SHAR&type=VIRL&format=json&oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("updates.json", getClass()), MediaType.APPLICATION_JSON));
		List<LinkedInNetworkUpdate> updates = linkedIn.networkUpdateOperations().getNetworkUpdates(10, 30);
		assertUpdates(updates);
	}
	
	private void assertUpdates(List<LinkedInNetworkUpdate> updates) {
		assertEquals(9, updates.size());
		
		// Connection Update
		assertUpdate(updates.get(0), UpdateType.CONN, UpdateContentConnection.class,
				new Date(1321282506000l), "CONN-6870400-*2-*1", false);
		assertProfile(updates.get(0).getUpdateContent(), "L95Bmv9vvv", "CEM Consultant at Smith", "Nicolas", "Smith", null, "");
		assertProfile( ((UpdateContentConnection)updates.get(0).getUpdateContent()).getConnections().get(0),
				"x3AmSzDvvv", "Director at Smith", "Sanjay", "Smith", null, "");
		
		// Share Update
		assertUpdate(updates.get(1), UpdateType.SHAR, UpdateContentShare.class,
				new Date(1321280958582l), "UNIU-2481200-5541854009700100000-SHARE", true);
		assertProfile(updates.get(1).getUpdateContent(), "r90Z7yavvv", "VP Sales & Operations at Smith", "Niall", "Smith", null, "");
		assertShare(((UpdateContentShare)updates.get(1).getUpdateContent()).getCurrentShare(), 
				"s699246000", "anyone", "TWITTER", "niall_smith", "8427000", "136088316402614000",
				"Take a photo tour of what the new Kindle Fire has to offer. http://t.co/cEXESrIv via @CNET #li");
		assertProfile(((UpdateContentShare)updates.get(1).getUpdateContent()).getCurrentShare().getAuthor(), "r90Z7yavvv", "VP Sales & Operations at Smith", "Niall", "Smith", null, "");
		
		// Profile Update
		assertUpdate(updates.get(2), UpdateType.PROF, UpdateContent.class,
				new Date(1321271141533l), "PROF-78067750-5541812856741100000-*1", true);
		assertArrayEquals(updates.get(2).getUpdatedFields().toArray(), new String[] {"person/headline", "person/specialties", "person/positions"});
		
		// Group Update
		assertUpdate(updates.get(3), UpdateType.JGRP, UpdateContentGroup.class,
				new Date(1321271102476l), "JGRP-2481200-5541812670493100000-*1", true);
		assertGroup(((UpdateContentGroup)updates.get(3).getUpdateContent()).getMemberGroups().get(0), "130889", "Irish Executives", "http://www.linkedin.com/groups?gid=130889");
		
		// Viral Update (Contains Share Update embedded)
		assertUpdate(updates.get(4), UpdateType.VIRL, UpdateContentViral.class,
				new Date(1321271057014l), "UNIU-28432557-5541812479593500000-VIRAL", false);
		assertShare(((UpdateContentShare)((UpdateContentViral)updates.get(4).getUpdateContent()).getUpdateAction().getUpdateContent()).getCurrentShare(),
				"s698865000", "anyone", "LINKEDIN", null, null, null, 
				"The NOW Factory ISA Company of the Year 2011");
		
		// Profile Picture Update
		assertUpdate(updates.get(5), UpdateType.PICU, UpdateContent.class,
				new Date(1321269958918l), "PICU-78067750-5541807874285500000-*1", true);
		assertProfile(updates.get(5).getUpdateContent(), "B5Set8lvvv", "Enterprise Officer at Smith", "john", "Smith", null, "");
		
		// Recommended Update
		assertUpdate(updates.get(6), UpdateType.PREC, UpdateContentRecommendation.class,
				new Date(1320771064517l), "PREC-25073976-5539715374557100000-*1", true);
		assertRecommendation(((UpdateContentRecommendation)updates.get(6).getUpdateContent()).getRecommendationsReceived().get(0),
				"Over the past 5 years, I have been fortunate enough to work with John and his team at Smith acros...", RecommendationType.BUSINESS_PARTNER);
		assertProfile(((UpdateContentRecommendation)updates.get(6).getUpdateContent()).getRecommendationsReceived().get(0).getRecommender(), 
				"Zo4xt_cvvv", "Internet Business Developer and SEO", "David", "Smith", null, "");
		
		// Recommend Update
		assertUpdate(updates.get(7), UpdateType.PREC, UpdateContentRecommendation.class,
				new Date(1321307620783l), "PREC-7024000-5541965839374225000-*1", true);
		assertRecommendation(((UpdateContentRecommendation)updates.get(7).getUpdateContent()).getRecommendationsGiven().get(0),
				"I have worked with Chris on a number of projects in Smith.  In all of that time ...", RecommendationType.COLLEAGUE);
		assertProfile(((UpdateContentRecommendation)updates.get(7).getUpdateContent()).getRecommendationsGiven().get(0).getRecommendee(), 
				"2nnDBUHvvv", "Information Technology and Services Professional", "Chris", "Smith", null, "");
		
		// Activity Update
		assertUpdate(updates.get(8), UpdateType.APPM, UpdateContentPersonActivity.class,
				new Date(1321285689160l), "APPM-7024701-554187385169820000-1700", true);
		assertPersonActivity(((UpdateContentPersonActivity)updates.get(8).getUpdateContent()).getPersonActivities().get(0),
				1700, "<a href=\"http://www.linkedin.com//profile?viewProfile=&key=7024000\">Paul O&#39;Smith</a> recommends <a href=\"http://www.linkedin.com//redirect?url=http%3A%2F%2Fwww%2Elinkedin%2Ecom%2Fosview%2Fcanvas%3F_ch_page_id%3D1%26_ch_panel_id%3D3%26_ch_app_id%3D20%26_applicationId%3D1700%26_ownerId%3D7024701%26osUrlHash%3DepGa%26appParams%3D%257B%2522view%2522%253A%2522book%2522%252C%2522asin%2522%253A%25220446563048%2522%252C%2522offset%2522%253A%25220%2522%257D&urlhash=aA19\">Delivering Happiness: A Path to Profits, Passion, and Purpose</a>");
	}
	
}
