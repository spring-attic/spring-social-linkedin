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
package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Model class containing a user's LinkedIn profile information.
 * 
 * @author Craig Walls
 */
public class LinkedInProfile extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String headline;
	
	private String industry;

	private String emailAddress;
	
	private UrlResource siteStandardProfileRequest;
	
	private String publicProfileUrl;
	
	private String pictureUrl;
	
	private String summary;
	
	private ConnectionAuthorization connectionAuthorization;
	
	public LinkedInProfile(String id, String firstName, String lastName, String headline, String industry, String publicProfileUrl, UrlResource siteStandardProfileRequest, String pictureUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.headline = headline;
		this.industry = industry;
		this.publicProfileUrl = publicProfileUrl;
		this.siteStandardProfileRequest = siteStandardProfileRequest;
		this.pictureUrl = pictureUrl;
	}

	
	public LinkedInProfile() {
		super();
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
	 * The user's email address (if available).
	 * Requires "r_emailaddress" scope; will be null if "r_emailaddress" scope is not authorized on the connection.
	 */
	public String getEmailAddress() {
		return emailAddress;
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
	public String getPictureUrl() {
		return pictureUrl;
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


	public void setId(String id) {
		this.id = id;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setHeadline(String headline) {
		this.headline = headline;
	}


	public void setIndustry(String industry) {
		this.industry = industry;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public void setSiteStandardProfileRequest(UrlResource siteStandardProfileRequest) {
		this.siteStandardProfileRequest = siteStandardProfileRequest;
	}


	public void setPublicProfileUrl(String publicProfileUrl) {
		this.publicProfileUrl = publicProfileUrl;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public void setConnectionAuthorization(
			ConnectionAuthorization connectionAuthorization) {
		this.connectionAuthorization = connectionAuthorization;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	
}
