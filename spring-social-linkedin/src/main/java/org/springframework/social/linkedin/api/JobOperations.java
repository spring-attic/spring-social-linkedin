package org.springframework.social.linkedin.api;


/**
 * Operations on Linkedin Jobs API
 * 
 * @author Robert Drysdale
 *
 */
public interface JobOperations {
	/**
	 * Search form Jobs
	 * @param parameters Various parameters that control behaviour of search
	 * @return Search Result
	 */
	SearchResultJob searchJobs(JobSearchParameters parameters);
	
	/**
	 * Retreive and Job by ID
	 * 
	 * @param id Numerical id of job
	 * @return Job
	 */
	Job getJob(int id);
	
	/**
	 * Bookmark Job
	 * 
	 * @param id
	 */
	void bookmarkJob(int id);
	
	/**
	 * Unbookmark Job
	 * 
	 * @param id
	 */
	void unbookmarkJob(int id);
	
	/**
	 * Get suggested list of jobs
	 * @param start First job to return
	 * @param count Number of jobs to return
	 * @return Job Search Result
	 */
	SearchResultJob getSuggestions(int start, int count);
	
	/**
	 * Get List of Bookmarked Jobs
	 * 
	 * @param start First job to return
	 * @param count Number of jobs to return
	 * @return Job Bookmark Result
	 */
	JobBookmarkResult getBookmarks(int start, int count);
}
