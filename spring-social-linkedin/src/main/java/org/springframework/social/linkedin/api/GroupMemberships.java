package org.springframework.social.linkedin.api;

import java.util.List;

public class GroupMemberships extends SearchResult {
	private static final long serialVersionUID = 1L;
	
	private List<GroupSettings> memberships;
	
	public GroupMemberships(int count, int start, int total) {
		super(count, start, total);
	}
	
	public List<GroupSettings> getMemberships() {
		return memberships;
	}

}
