package org.springframework.social.linkedin.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.Job;
import org.springframework.social.linkedin.api.JobBookmarkResult;
import org.springframework.social.linkedin.api.JobOperations;
import org.springframework.social.linkedin.api.JobSearchParameters;
import org.springframework.social.linkedin.api.SearchResultJob;
import org.springframework.web.client.RestTemplate;

/**
 * Class that implements Job API
 * 
 * @author Robert Drysdale
 *
 */
public class JobTemplate extends AbstractTemplate implements JobOperations {
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	
	public JobTemplate(RestTemplate restTemplate, ObjectMapper objectMapper ) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}
	
	public SearchResultJob searchJobs(JobSearchParameters parameters) {
		Object[] params = new Object[] {
				parameters.getKeywords(),
				parameters.getCompanyName(),
				parameters.getJobTitle(),
				parameters.getCountryCode(),
				parameters.getPostalCode(),
				parameters.getDistance(),
				parameters.getStart(),
				parameters.getCount(),
				parameters.getSort()
		};
		
		JsonNode node = restTemplate.getForObject(expand(SEARCH_URL, params, true), JsonNode.class);
		
		try {
			return objectMapper.readValue(node.path("jobs"), new TypeReference<SearchResultJob>() {});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Job getJob(int id) {
		return restTemplate.getForObject(JOB_URL, Job.class, id);
	}
	
	public void bookmarkJob(int id) {
		Map<String, Map<String,Integer>> jobDetails = new HashMap<String,Map<String,Integer>>();
		Map<String,Integer>idDetails = new HashMap<String,Integer>();
		jobDetails.put("job", idDetails);
		idDetails.put("id", id);
		restTemplate.postForLocation(BOOKMARK_URL, jobDetails);
	}
	
	public void unbookmarkJob(int id) {
		restTemplate.delete(UNBOOKMARK_URL, id);
	}
	
	public SearchResultJob getSuggestions(int start, int count) {
		JsonNode node =  restTemplate.getForObject(expand(SUGGESTED_URL, new Object[] {start,count}, false), JsonNode.class);
		
		try {
			return objectMapper.readValue(node.path("jobs"), new TypeReference<SearchResultJob>() {});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public JobBookmarkResult getBookmarks(int start, int count) {
		return restTemplate.getForObject(expand(BOOKMARKS_URL, new Object[] {start,count}, false), JobBookmarkResult.class);
		
	}
	
	public static final String BASE_URL = "https://api.linkedin.com/v1/";
	public static final String FIELDS = "(id,customer-job-code,active,posting-date,expiration-date,posting-timestamp,expiration-timestamp,company:(id,name),position:(title,location,job-functions,industries,job-type,experience-level),skills-and-experience,description-snippet,description,salary,job-poster:(id,first-name,last-name,headline),referral-bonus,site-job-url,location-description)";
	public static final String SEARCH_FIELDS = "(jobs:" + FIELDS + ")";
	public static final String SEARCH_URL = BASE_URL + "job-search:" + SEARCH_FIELDS + "?{&keywords}{&company-name}{&job-title}{&country-code}{&postal-code}{&distance}{&start}{&count}{&sort}";
	public static final String JOB_URL = BASE_URL + "jobs/{id}:" + FIELDS;
	public static final String BOOKMARK_URL = BASE_URL + "people/~/job-bookmarks";
	public static final String BOOKMARKS_URL = BOOKMARK_URL + "?{&start}{&count}";
	public static final String UNBOOKMARK_URL = BASE_URL + "people/~/job-bookmarks/{job-id}";
	public static final String SUGGESTED_URL = BASE_URL + "people/~/suggestions/job-suggestions:" + SEARCH_FIELDS + "?{&start}{&count}";
}
