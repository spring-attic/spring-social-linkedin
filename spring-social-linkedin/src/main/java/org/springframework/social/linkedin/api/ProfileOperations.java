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

/**
 * Operations on Profile API
 * 
 * @author Robert Drysdale
 */
public interface ProfileOperations {

	/**
	 * Retrieves the user's LinkedIn profile ID.
	 * 
	 * @return the user's LinkedIn profile ID.
	 */
	String getProfileId();

	/**
	 * Retrieves a URL to the user's public profile page.
	 * 
	 * @return a URL to the user's public profile page.
	 */
	String getProfileUrl();

	/**
	 * Retrieves the current user's profile details.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfile getUserProfile();
	
	/**
	 * Retrieves a profile by id.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfile getProfileById(String id);
	
	/**
	 * Retrieves a profile by public url.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfile getProfileByPublicUrl(String url);
	
	
	/**
	 * Retrieves the current user's full profile details.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfileFull getUserProfileFull();
	
	/**
	 * Retrieves  the current users basic profile and primary email address
	 * 
	 * @return the uesr's profile with basic profile fields and email address
	 */
	LinkedInProfileFull getProfileWithEmailAddress();
	
	/**
	 * Retrieves a full profile by id.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfileFull getProfileFullById(String id);
	
	/**
	 * Retrieves the profile - basic fields and the user's primary email address
	 * 
	 * @return the user's profile with basic profile fields and email address
	 */
	LinkedInProfileFull getProfileWithEmailAddressById(String id);
	
	/**
	 * Retrieves the profile - basic fields and the user's primary email address by public url
	 * 
	 * @return the user's profile data with the basic fields and email address
	 */
	LinkedInProfileFull getProfileWithEmailByPublicUrl(String url);	
	
	/**
	 * Retrieves a full profile by public url.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfileFull getProfileFullByPublicUrl(String url);
	
	/**
	 * Searches for Profiles based on provided parameters
	 * 
	 * @return search result.
	 */
	LinkedInProfiles search(SearchParameters parameters);

}
