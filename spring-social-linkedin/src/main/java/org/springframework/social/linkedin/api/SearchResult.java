package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int count;
	private final int start;
	private final int total;
	private final List<LinkedInProfile> people;
	
	public SearchResult(int count, int start, int total, List<LinkedInProfile> people) {
		this.count = count;
		this.start = start;
		this.total = total;
		this.people = people;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getTotal() {
		return total;
	}
	
	public List<LinkedInProfile> getPeople() {
		return people;
	}
}
