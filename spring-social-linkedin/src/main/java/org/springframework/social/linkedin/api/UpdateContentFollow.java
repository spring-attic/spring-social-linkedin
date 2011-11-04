package org.springframework.social.linkedin.api;

public class UpdateContentFollow extends UpdateContent {

	private static final long serialVersionUID = 1L;
	
	private String action;
	
	private Company following;
	
	public UpdateContentFollow(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}
	
	public String getAction() {
		return action;
	}
	
	public Company getFollowing() {
		return following;
	}

}
