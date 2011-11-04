package org.springframework.social.linkedin.api;

import java.util.List;



public class UpdateContentConnection extends UpdateContent {
	private static final long serialVersionUID = 1L;
	
	private List<LinkedInProfile> connections;
	
	public UpdateContentConnection(String id, String firstName,
			String lastName, String headline, String industry,
			String publicProfileUrl, String standardProfileUrl,
			String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}
	
	public List<LinkedInProfile> getConnections() {
		return connections;
	}

}
