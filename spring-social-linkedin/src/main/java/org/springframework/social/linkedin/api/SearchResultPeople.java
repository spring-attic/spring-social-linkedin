package org.springframework.social.linkedin.api;

import java.util.List;

public class SearchResultPeople extends SearchResult {
	private static final long serialVersionUID = 1L;

	public SearchResultPeople(int count, int start, int total) {
		super(count, start, total);
	}

	private List<LinkedInProfile> people;
	
	public List<LinkedInProfile> getPeople() {
		return people;
	}
}
