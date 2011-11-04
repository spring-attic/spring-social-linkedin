package org.springframework.social.linkedin.api;

import java.util.List;

public class UpdateContentPersonActivity extends UpdateContent {

	private static final long serialVersionUID = 1L;
	
	private List<PersonActivity> personActivities;
	
	public UpdateContentPersonActivity(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}
	
	public List<PersonActivity> getPersonActivities() {
		return personActivities;
	}
	
	

}
