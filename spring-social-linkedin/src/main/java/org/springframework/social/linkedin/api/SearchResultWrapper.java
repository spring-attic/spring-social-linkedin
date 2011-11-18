package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class SearchResultWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final SearchResult result;
	
	public SearchResultWrapper(SearchResult result) {
		this.result = result;
	}
	
	public SearchResult getResult() {
		return result;
	}
}
