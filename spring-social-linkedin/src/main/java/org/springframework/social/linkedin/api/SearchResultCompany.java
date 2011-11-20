package org.springframework.social.linkedin.api;

import java.util.List;

public class SearchResultCompany extends SearchResult {
	private static final long serialVersionUID = 1L;
	
	private List<Company> companies;

	public SearchResultCompany(int count, int start, int total) {
		super(count, start, total);
	}
	
	public List<Company> getCompanies() {
		return companies;
	}

}
