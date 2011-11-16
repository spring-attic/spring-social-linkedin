package org.springframework.social.linkedin.api.impl;

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.BASE_URL;

import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.ProfileOperations;
import org.springframework.web.client.RestTemplate;

public class ProfileTemplate implements ProfileOperations {
	private RestTemplate restTemplate;
	
	public ProfileTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	public String getProfileId() {
		return getUserProfile().getId();
	}

	public String getProfileUrl() {
		return getUserProfile().getPublicProfileUrl();
	}

	public LinkedInProfile getUserProfile() {
		return restTemplate.getForObject(PROFILE_URL, LinkedInProfile.class);
	}
	
	static final String PROFILE_URL = BASE_URL + "~:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json";

}
