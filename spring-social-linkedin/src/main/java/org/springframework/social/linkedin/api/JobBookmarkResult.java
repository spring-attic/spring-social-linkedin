package org.springframework.social.linkedin.api;

import java.util.List;

/**
 * Result returned from querying Job Bookmarks
 * 
 * @author Robert Drysdale
 *
 */
public class JobBookmarkResult extends SearchResult {

	private static final long serialVersionUID = 1L;
	
	private List<JobBookmark> jobBookmarks;

	public JobBookmarkResult(int count, int start, int total) {
		super(count, start, total);
	}
	
	public List<JobBookmark> getJobBookmarks() {
		return jobBookmarks;
	}

}
