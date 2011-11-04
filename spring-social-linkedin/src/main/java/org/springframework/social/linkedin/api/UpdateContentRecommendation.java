package org.springframework.social.linkedin.api;

import java.util.List;

public class UpdateContentRecommendation extends UpdateContent {

	private static final long serialVersionUID = 1L;
	
	private List<Recommendation> recommendations;
	
	public UpdateContentRecommendation(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}
	
	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

}
