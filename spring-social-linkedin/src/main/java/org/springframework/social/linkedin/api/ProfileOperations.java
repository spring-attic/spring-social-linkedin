package org.springframework.social.linkedin.api;

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
	 * Retrieves a full profile by id.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfileFull getProfileFullById(String id);
	
	
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
	SearchResultPeople search(SearchParameters parameters) ;
}
