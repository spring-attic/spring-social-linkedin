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
package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Model class containing a user's LinkedIn profile information.
 * 
 * @author Craig Walls
 */
public class LinkedInProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String id;
	
	private final String firstName;
	
	private final String lastName;
	
	private final String headline;
	
	private final String industry;
	
	private final UrlResource siteStandardProfileRequest;
	
	private final String publicProfileUrl;
	
	private final String profilePictureUrl;
	
	private String summary;
	
	private ConnectionAuthorization connectionAuthorization;
	
	public LinkedInProfile(String id, String firstName, String lastName, String headline, String industry, String publicProfileUrl, UrlResource siteStandardProfileRequest, String profilePictureUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.headline = headline;
		this.industry = industry;
		this.publicProfileUrl = publicProfileUrl;
		this.siteStandardProfileRequest = siteStandardProfileRequest;
		this.profilePictureUrl = profilePictureUrl;
	}

	/**
	 * The user's LinkedIn profile ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * The user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * The user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * The user's headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * The user's industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * A URL to the user's standard profile. The content shown at this profile will depend upon what the requesting user is allowed to see.
	 */
	public UrlResource getSiteStandardProfileRequest() {
		return siteStandardProfileRequest;
	}

	/**
	 * A URL to the user's public profile. The content shown at this profile is intended for public display and is determined by the user's privacy settings.
	 * May be null if the user's profile isn't public.
	 */
	public String getPublicProfileUrl() {
		return publicProfileUrl;
	}
	
	/**
	 * A URL to the user's profile picture.
	 */
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	
	/**
	 * The user's summary.
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * @return Authorization information required for connecting to this user.
	 */
	public ConnectionAuthorization getConnectionAuthorization() {
		return connectionAuthorization;
	}

}
