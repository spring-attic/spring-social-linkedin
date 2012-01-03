package org.springframework.social.linkedin.api;

import java.util.List;

/**
 * Search result for querying jobs
 * 
 * @author Robert Drysdale
 *
 */
public class SearchResultJob extends SearchResult {

	private static final long serialVersionUID = 1L;
	
	private List<Job> jobs;

	public SearchResultJob(int count, int start, int total) {
		super(count, start, total);
	}
	
	public List<Job> getJobs() {
		return jobs;
	}

}
