package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Abstract class for Search and certain other types of query list results
 * 
 * @author Robert Drysdale
 *
 */
public abstract class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int count;
	private final int start;
	private final int total;
	
	public SearchResult(int count, int start, int total) {
		this.count = count;
		this.start = start;
		this.total = total;
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
}
