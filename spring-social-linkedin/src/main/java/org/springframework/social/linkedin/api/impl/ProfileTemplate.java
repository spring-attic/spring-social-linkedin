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

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.social.ApiException;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.api.LinkedInProfiles;
import org.springframework.social.linkedin.api.ProfileField;
import org.springframework.social.linkedin.api.ProfileOperations;
import org.springframework.social.linkedin.api.SearchParameters;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class that implements operations for Profile API
 * 
 * @author Robert Drysdale
 * @author Rosty Kerei
 */
class ProfileTemplate extends AbstractTemplate implements ProfileOperations {


	static {
		StringBuffer b = new StringBuffer();
		b.append(":(");
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
		FULL_PROFILE_FIELDS = b.toString();
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
		return getUserProfile(PROFILE_FIELDS, LinkedInProfile.class);
	}
	
	public LinkedInProfileFull getUserProfileFull() {
		return getUserProfile(FULL_PROFILE_FIELDS, LinkedInProfileFull.class);
	}
	
	public LinkedInProfile getProfileById(String id) {
		return getProfileFullById(id, PROFILE_FIELDS, LinkedInProfile.class);
	}
	
	public LinkedInProfile getProfileByPublicUrl(String url) {
		return getProfileByPublicUrl(url, PROFILE_FIELDS, LinkedInProfile.class);
	}
	
	public LinkedInProfileFull getProfileFullById(String id) {
		return getProfileFullById(id, FULL_PROFILE_FIELDS, LinkedInProfileFull.class);
	}
	
	public LinkedInProfileFull getProfileFullByPublicUrl(String url) {
		return getProfileByPublicUrl(url, FULL_PROFILE_FIELDS, LinkedInProfileFull.class);
	}
	
	public LinkedInProfiles search(SearchParameters parameters) {
		JsonNode node =  restOperations.getForObject(expand(PEOPLE_SEARCH_URL, parameters), JsonNode.class);
		try {
			return objectMapper.reader(LinkedInProfiles.class).readValue(node.path("people"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	private <T> T getUserProfile(String fields, Class<T> type) {
		return restOperations.getForObject(URIBuilder.fromUri(BASE_URL + "~" + fields).build(), type);
	}
	
	private <T> T getProfileFullById(String id, String fields, Class<T> type) {
		return restOperations.getForObject(URIBuilder.fromUri(BASE_URL + "id=" + id + fields).build(), type);
	}
	
	private <T> T getProfileByPublicUrl(String url, String fields, Class<T> type) {
		try {
			URI uri = URIBuilder.fromUri(BASE_URL + "url=" + URLEncoder.encode(url, "UTF-8") + fields).build();
			return restOperations.exchange(uri, HttpMethod.GET, new HttpEntity<String>(""), type).getBody();
		} catch (UnsupportedEncodingException unlikely) {
			unlikely.printStackTrace();
			throw new ApiException("linkedin", "Unlikely unsupported encoding error", unlikely);
		}		
	}
	

	private URI expand(String url, SearchParameters parameters) {
		URIBuilder uriBuilder =URIBuilder.fromUri(url);

		if (parameters.getKeywords() != null) {
			uriBuilder.queryParam("keywords", parameters.getKeywords());
		}
		
		if (parameters.getFirstName() != null) {
			uriBuilder.queryParam("first-name", parameters.getFirstName());
		}
		
		if (parameters.getLastName() != null) {
			uriBuilder.queryParam("last-name", parameters.getLastName());
		}
		
		if (parameters.getCompanyName() != null) {
			uriBuilder.queryParam("company-name", parameters.getCompanyName());
		}
		
		if (parameters.getCurrentCompany() != null) {
			uriBuilder.queryParam("current-company", parameters.getCurrentCompany().toString());
		}
		
		if (parameters.getTitle() != null) {
			uriBuilder.queryParam("title", parameters.getTitle());
		}
		
		if (parameters.getCurrentTitle() != null) {
			uriBuilder.queryParam("current-title", parameters.getCurrentTitle().toString());
		}
		
		if (parameters.getSchoolName() != null) {
			uriBuilder.queryParam("school-name", parameters.getSchoolName());
		}
		
		if (parameters.getCurrentSchool() != null) {
			uriBuilder.queryParam("current-school", parameters.getCurrentSchool().toString());
		}
		
		if (parameters.getCountryCode() != null) {
			uriBuilder.queryParam("country-code", parameters.getCountryCode());
		}
		
		if (parameters.getPostalCode() != null) {
			uriBuilder.queryParam("postal-code", parameters.getPostalCode());
		}
		
		if (parameters.getDistance() != null) {
			uriBuilder.queryParam("distance", parameters.getDistance().toString());
		}
		
		uriBuilder.queryParam("start", String.valueOf(parameters.getStart()));
		
		uriBuilder.queryParam("count", String.valueOf(parameters.getCount()));
		
		if (parameters.getSort() != null) {
			uriBuilder.queryParam("sort", parameters.getSort().toString());
		}
		
		// Processing facets
		
		MultiValueMap<SearchParameters.FacetType, String> facets = parameters.getFacets();
		
		for (SearchParameters.FacetType ft : facets.keySet()) {
			StringBuilder facetString = new StringBuilder(ft.toString());
		
			for (String value : facets.get(ft)) {
				facetString.append(",");
				facetString.append(value);
			}
		
			uriBuilder.queryParam("facet", facetString.toString());
		}
		
		return uriBuilder.build();
	}
	
	static final String PROFILE_FIELDS = ":(id,first-name,last-name,emailAddress,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json";
	
	static final String FULL_PROFILE_FIELDS;

	static final String PEOPLE_SEARCH_URL = "https://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary,api-standard-profile-request))";

}
