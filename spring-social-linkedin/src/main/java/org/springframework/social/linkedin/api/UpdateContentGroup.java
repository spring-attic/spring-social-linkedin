package org.springframework.social.linkedin.api;

import java.util.List;

public class UpdateContentGroup extends UpdateContent {

	private static final long serialVersionUID = 1L;
	
	private List<MemberGroup> memberGroups;
	
	public UpdateContentGroup(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				standardProfileUrl, profilePictureUrl);
	}
	
	public List<MemberGroup> getMemberGroups() {
		return memberGroups;
	}
	
	

}
