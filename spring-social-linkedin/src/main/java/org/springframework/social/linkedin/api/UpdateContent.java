package org.springframework.social.linkedin.api;


public class UpdateContent extends LinkedInProfile {

	private static final long serialVersionUID = 1L;
	
	public UpdateContent(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}

}
