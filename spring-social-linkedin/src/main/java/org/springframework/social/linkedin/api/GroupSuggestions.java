package org.springframework.social.linkedin.api;

import java.util.List;

public class GroupSuggestions extends SearchResult {
	private static final long serialVersionUID = 1L;
	
	private List<Group> suggestions;
	
	public GroupSuggestions(int count, int start, int total) {
		super(count, start, total);
	}
	
	public List<Group> getSuggestions() {
		return suggestions;
	}

}
