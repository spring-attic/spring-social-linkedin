/*
 * Copyright 2012 the original author or authors.
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

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.*;

import java.net.URI;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.api.ProfileField;
import org.springframework.social.linkedin.api.ProfileOperations;
import org.springframework.social.linkedin.api.SearchParameters;
import org.springframework.social.linkedin.api.SearchResultPeople;
import org.springframework.web.client.RestOperations;

/**
 * Class that implements operations for Profile API
 * 
 * @author Robert Drysdale
 *
 */
public class ProfileTemplate extends AbstractTemplate implements ProfileOperations {
	static {
		StringBuffer b = new StringBuffer();
		b.append(BASE_URL).append("{id}:(");
		boolean first = true;
		for (ProfileField f : ProfileField.values()) {
			switch (f) {
			case CONNECTIONS:
				break;
			default:
				if (first) {
					first = false;
				}
				else {
					b.append(',');
				}
				b.append(f);
			}
		}
		b.append(")?format=json");
		
		PROFILE_URL_FULL = b.toString();
	}
	
	private RestOperations restOperations;
	private ObjectMapper objectMapper;
	
	public ProfileTemplate(RestOperations restOperations, ObjectMapper objectMapper) {
		this.restOperations = restOperations;
		this.objectMapper = objectMapper;
	}
	public String getProfileId() {
		return getUserProfile().getId();
	}

	public String getProfileUrl() {
		return getUserProfile().getPublicProfileUrl();
	}

	public LinkedInProfile getUserProfile() {
		return restOperations.getForObject(PROFILE_URL, LinkedInProfile.class, "~");
	}
	
	public LinkedInProfileFull getUserProfileFull() {
		return restOperations.getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, "~");
	}
	
	public LinkedInProfile getProfileById(String id) {
		return restOperations.getForObject(PROFILE_URL, LinkedInProfile.class, "id=" + id);
	}
	
	public LinkedInProfile getProfileByPublicUrl(String url) {
		return restOperations.getForObject(PROFILE_URL, LinkedInProfile.class, "url=" + url);
	}
	
	public LinkedInProfileFull getProfileFullById(String id) {
		return restOperations.getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, "id=" + id);
	}
	
	public LinkedInProfileFull getProfileFullByPublicUrl(String url) {
		return restOperations.getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, "url=" + url);
	}
	
	public SearchResultPeople search(SearchParameters parameters) {
		JsonNode node =  restOperations.getForObject(expand(PEOPLE_SEARCH_URL, parameters), JsonNode.class);
		try {
			return objectMapper.readValue(node.path("people"), SearchResultPeople.class);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	private URI expand(String url, SearchParameters parameters) {
		Object[] variables = new Object[] {
				parameters.getKeywords(),
				parameters.getFirstName(),
				parameters.getLastName(),
				parameters.getCompanyName(),
				parameters.getCurrentCompany(),
				parameters.getTitle(),
				parameters.getCurrentTitle(),
				parameters.getSchoolName(),
				parameters.getCurrentSchool(),
				parameters.getCountryCode(),
				parameters.getPostalCode(),
				parameters.getDistance(),
				parameters.getStart(),
				parameters.getCount(),
				parameters.getSort()
		};
		return expand(url, variables, true);
	}
	
	static final String PROFILE_URL = BASE_URL + "{id}:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json";
	
	static final String PROFILE_URL_FULL;
	
	static final String PEOPLE_SEARCH_URL = "https://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary,api-standard-profile-request))?{&keywords}{&first-name}{&last-name}{&company-name}{&current-company}{&title}{&current-title}{&school-name}{&current-school}{&country-code}{&postal-code}{&distance}{&start}{&count}{&sort}";

}
