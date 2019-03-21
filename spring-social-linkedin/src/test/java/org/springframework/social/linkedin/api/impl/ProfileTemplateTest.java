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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.api.LinkedInProfiles;
import org.springframework.social.linkedin.api.Recommendation.RecommendationType;
import org.springframework.social.linkedin.api.SearchParameters;

/**
 * @author Craig Walls
 */
public class ProfileTemplateTest extends AbstractLinkedInApiTest {

	@Test
	public void getUserProfile() {
		mockServer.expect(requestTo(LinkedInTemplate.BASE_URL + "~" + ProfileTemplate.PROFILE_FIELDS + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("profile.json", getClass()), MediaType.APPLICATION_JSON));
		LinkedInProfile profile = linkedIn.profileOperations().getUserProfile();
		assertEquals("z37f0n3A05", profile.getId());
		assertEquals("Just a guy", profile.getHeadline());
		assertEquals("Craig", profile.getFirstName());
		assertEquals("Walls", profile.getLastName());
		assertEquals("Computer Software", profile.getIndustry());
		assertEquals("https://www.linkedin.com/in/habuma", profile.getPublicProfileUrl());
		assertEquals("https://www.linkedin.com/standardProfileUrl", profile.getSiteStandardProfileRequest().getUrl());
		assertEquals("https://media.linkedin.com/pictureUrl", profile.getProfilePictureUrl());
	}
	
	@Test 
	public void getUserProfileFull() {
		mockServer.expect(requestTo(LinkedInTemplate.BASE_URL + "~" + ProfileTemplate.FULL_PROFILE_FIELDS + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("profile_full.json", getClass()), MediaType.APPLICATION_JSON));
		
		LinkedInProfileFull profile = linkedIn.profileOperations().getUserProfileFull();
		
		assertProfile(profile, "UB2kruYmAL", "Software Architect", "Robert", "Drysdale", "Telecommunications", null);
		
		assertEquals("Canoeing Ireland", profile.getAssociations());
		assertEquals(1900, profile.getDateOfBirth().getYear());
		assertEquals(1, profile.getDateOfBirth().getMonth());
		assertEquals(1, profile.getDateOfBirth().getDay());
		assertEquals(3, profile.getEducations().size());
		assertEquals("MSc Innovation & Technology Management", profile.getEducations().get(0).getDegree());
		assertEquals("University College Dublin", profile.getEducations().get(0).getSchoolName());
		assertEquals(2009, profile.getEducations().get(0).getEndDate().getYear());
		assertEquals(2007, profile.getEducations().get(0).getStartDate().getYear());
		assertEquals("None", profile.getHonors());
		assertEquals(1, profile.getImAccounts().size());
		assertEquals("robbiedrysdale", profile.getImAccounts().get(0).getImAccountName());
		assertEquals("skype", profile.getImAccounts().get(0).getImAccountType());
		assertEquals("Telecommunications", profile.getIndustry());
		assertEquals("ie", profile.getLocation().getCountry());
		assertEquals("Ireland", profile.getLocation().getName());
		assertEquals(189, profile.getNumConnections());
		assertEquals("Dublin, Ireland", profile.getMainAddress());
		assertEquals(1, profile.getMemberUrlResources().size());
		assertEquals("Company Website", profile.getMemberUrlResources().get(0).getName());
		assertEquals("https://www.robatron.com", profile.getMemberUrlResources().get(0).getUrl());
		assertEquals(2, profile.getNumRecommenders());
		assertEquals("+353 87 9580000", profile.getPhoneNumbers().get(0).getPhoneNumber());
		assertEquals("mobile", profile.getPhoneNumbers().get(0).getPhoneType());
		assertEquals(8, profile.getPositions().size());
		assertEquals("133861560", profile.getPositions().get(0).getId());
		assertEquals(true, profile.getPositions().get(0).getIsCurrent());
		assertEquals(2010, profile.getPositions().get(0).getStartDate().getYear());
		assertEquals(6, profile.getPositions().get(0).getStartDate().getMonth());
		assertEquals(0, profile.getPositions().get(0).getStartDate().getDay());
		assertEquals("CBW at robatron, a Media Streaming startup.  Ongoing Technology research into potential new products.", profile.getPositions().get(0).getSummary());
		assertEquals("CBW", profile.getPositions().get(0).getTitle());
		assertEquals("Computer Software", profile.getPositions().get(0).getCompany().getIndustry());
		assertEquals("robatron", profile.getPositions().get(0).getCompany().getName());
		assertEquals(2, profile.getRecommendationsReceived().size());
		assertEquals("Great guy to work with, open, friendly and approachable as well as very knowledgeable on a wide range of areas outside the immediate remit of standard java development. In all an asset to any team, would be happy to work with him again.", profile.getRecommendationsReceived().get(0).getRecommendationText());
		assertEquals(RecommendationType.COLLEAGUE, profile.getRecommendationsReceived().get(0).getRecommendationType());
		assertEquals("Damien", profile.getRecommendationsReceived().get(0).getRecommender().getFirstName());
		assertEquals(3, profile.getSkills().size());
		assertEquals("Java", profile.getSkills().get(0));
		assertEquals(206,profile.getSpecialties().length());
		assertEquals(462,profile.getSummary().length());
		assertEquals("robdrysdale",profile.getTwitterAccounts().get(0).getProviderAccountName());
	}

	@Test
	public void getProfileId() {
		mockServer.expect(requestTo(LinkedInTemplate.BASE_URL + "~" + ProfileTemplate.PROFILE_FIELDS + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("profile.json", getClass()), MediaType.APPLICATION_JSON));
		assertEquals("z37f0n3A05", linkedIn.profileOperations().getProfileId());
	}

	@Test
	public void getProfileUrl() {
		mockServer.expect(requestTo(LinkedInTemplate.BASE_URL + "~" + ProfileTemplate.PROFILE_FIELDS + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("profile.json", getClass()), MediaType.APPLICATION_JSON));
		assertEquals("https://www.linkedin.com/in/habuma", linkedIn.profileOperations().getProfileUrl());
	}
	
	@Test 
	public void search() {
		mockServer.expect(requestTo(
				"https://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary,api-standard-profile-request))?keywords=Java+J2EE&country-code=ie&start=0&count=10"
						 + "&oauth2_access_token=ACCESS_TOKEN"
				)).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("search.json", getClass()), MediaType.APPLICATION_JSON));
		
		SearchParameters parameters = new SearchParameters();
		parameters.setCountryCode("ie");
		parameters.setKeywords("Java J2EE");
		LinkedInProfiles result = linkedIn.profileOperations().search(parameters);
		assertEquals(0, result.getStart());
		assertEquals(10, result.getCount());
		assertEquals(110, result.getTotal());
		assertEquals(10, result.getPeople().size());
		
		assertProfile(result.getPeople().get(0), 
				"YeagNX-lsX", "IT Consultant at Harvey Nash PLC", "Michelle", "Daly", "Staffing and Recruiting", null);
		
	}

	@Test
	public void searchFacet() {
		mockServer.expect(requestTo(
				"https://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary,api-standard-profile-request))?keywords=Java+J2EE&country-code=nl&start=0&count=10&facet=language%2Cen%2Cde&facet=network%2CF"
						 + "&oauth2_access_token=ACCESS_TOKEN"
		)).andExpect(method(GET))
		.andRespond(withSuccess(new ClassPathResource("search.json", getClass()), MediaType.APPLICATION_JSON));

		SearchParameters parameters = new SearchParameters();
		parameters.setCountryCode("nl");
		parameters.setKeywords("Java J2EE");
		
		parameters.addFacet(SearchParameters.FacetType.LANGUAGE, SearchParameters.LANGUAGE_ENGLISH);
		parameters.addFacet(SearchParameters.FacetType.LANGUAGE, SearchParameters.LANGUAGE_GERMAN);
		
		parameters.addFacet(SearchParameters.FacetType.NETWORK, SearchParameters.NETWORK_FIRST_DEGREE);
		
		LinkedInProfiles result = linkedIn.profileOperations().search(parameters);
		assertEquals(0, result.getStart());
		assertEquals(10, result.getCount());
		assertEquals(110, result.getTotal());
		assertEquals(10, result.getPeople().size());
		
		assertProfile(result.getPeople().get(0),
				"YeagNX-lsX", "IT Consultant at Harvey Nash PLC", "Michelle", "Daly", "Staffing and Recruiting", null);
	}
	
	@Test
	public void getProfileByPublicUrl() {
		mockServer.expect(requestTo(LinkedInTemplate.BASE_URL + "url=http%3A%2F%2Fwww.linkedin.com%2Fin%2Fhabuma" + ProfileTemplate.PROFILE_FIELDS + "&oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("profile.json", getClass()), MediaType.APPLICATION_JSON));
		
		LinkedInProfile profile = linkedIn.profileOperations().getProfileByPublicUrl("https://www.linkedin.com/in/habuma");
		assertEquals("z37f0n3A05", profile.getId());
		assertEquals("Just a guy", profile.getHeadline());
		assertEquals("Craig", profile.getFirstName());
		assertEquals("Walls", profile.getLastName());
		assertEquals("Computer Software", profile.getIndustry());
		assertEquals("https://www.linkedin.com/in/habuma", profile.getPublicProfileUrl());
		assertEquals("https://www.linkedin.com/standardProfileUrl", profile.getSiteStandardProfileRequest().getUrl());
		assertEquals("https://media.linkedin.com/pictureUrl", profile.getProfilePictureUrl());
	}

}
