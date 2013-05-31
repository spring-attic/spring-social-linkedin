/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.social.linkedin.api.Job;
import org.springframework.social.linkedin.api.JobBookmarks;
import org.springframework.social.linkedin.api.JobOperations;
import org.springframework.social.linkedin.api.JobSearchParameters;
import org.springframework.social.linkedin.api.Jobs;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class that implements Job API
 * 
 * @author Robert Drysdale
 */
class JobTemplate extends AbstractTemplate implements JobOperations {

	private final RestOperations restOperations;
	
	private final ObjectMapper objectMapper;
	
	public JobTemplate(RestOperations restOperations, ObjectMapper objectMapper ) {
		this.restOperations = restOperations;
		this.objectMapper = objectMapper;
	}
	
	public Jobs searchJobs(JobSearchParameters parameters) {
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
		
		JsonNode node = restOperations.getForObject(expand(SEARCH_URL, params, true), JsonNode.class);
		
		try {
			return objectMapper.reader(new TypeReference<Jobs>(){}).readValue(node.path("jobs"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Job getJob(int id) {
		return restOperations.getForObject(JOB_URL, Job.class, id);
	}
	
	public void bookmarkJob(int id) {
		Map<String, Map<String,Integer>> jobDetails = new HashMap<String,Map<String,Integer>>();
		Map<String,Integer>idDetails = new HashMap<String,Integer>();
		jobDetails.put("job", idDetails);
		idDetails.put("id", id);
		restOperations.postForLocation(BOOKMARK_URL, jobDetails);
	}
	
	public void unbookmarkJob(int id) {
		restOperations.delete(UNBOOKMARK_URL, id);
	}
	
	public Jobs getSuggestions(int start, int count) {
		JsonNode node =  restOperations.getForObject(expand(SUGGESTED_URL, new Object[] {start,count}, false), JsonNode.class);
		
		try {
			return objectMapper.reader(new TypeReference<Jobs>(){}).readValue(node.path("jobs"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public JobBookmarks getBookmarks(int start, int count) {
		return restOperations.getForObject(expand(BOOKMARKS_URL, new Object[] {start,count}, false), JobBookmarks.class);		
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
