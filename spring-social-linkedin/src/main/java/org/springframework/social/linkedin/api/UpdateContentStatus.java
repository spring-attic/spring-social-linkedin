package org.springframework.social.linkedin.api;

public class UpdateContentStatus extends UpdateContent {

	private static final long serialVersionUID = 1L;
	
	private String currentStatus;
	
	public UpdateContentStatus(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}
	
	public String getCurrentStatus() {
		return currentStatus;
	}

}
