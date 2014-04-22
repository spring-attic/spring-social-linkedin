/*
 * Copyright 2014 the original author or authors.
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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.Job;
import org.springframework.social.linkedin.api.JobBookmark;
import org.springframework.social.linkedin.api.JobBookmarks;
import org.springframework.social.linkedin.api.JobPosition;
import org.springframework.social.linkedin.api.JobSearchParameters;

public class JobTemplateTest extends AbstractLinkedInApiTest {

	
	@Test
	public void search() {
		mockServer.expect(requestTo(JobTemplate.SEARCH_URL
				.replaceFirst("\\{\\&keywords\\}", "keywords=j2ee")
				.replaceFirst("\\{\\&company-name\\}", "")
				.replaceFirst("\\{\\&job-title\\}", "")
				.replaceFirst("\\{\\&country-code\\}", "&country-code=ie")
				.replaceFirst("\\{\\&postal-code\\}", "")
				.replaceFirst("\\{\\&distance\\}", "")
				.replaceFirst("\\{\\&start\\}", "&start=0")
				.replaceFirst("\\{\\&count\\}", "&count=10")
				.replaceFirst("\\{\\&sort\\}", "") + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("job_search.json", getClass()), MediaType.APPLICATION_JSON));
		JobSearchParameters parameters = new JobSearchParameters();
		parameters.setCountryCode("ie");
		parameters.setKeywords("j2ee");
		List<Job> jobs = linkedIn.jobOperations().searchJobs(parameters).getJobs();
		
		assertEquals(8, jobs.size());
		Job j = jobs.get(0);
		assertEquals(true, j.isActive());
		assertEquals(139355, j.getCompany().getId());
		assertEquals("Allen Recruitment Consulting", j.getCompany().getName());
		assertEquals("5404", j.getCustomerJobCode());
		assertEquals(2755, j.getDescription().length());
		assertEquals(400, j.getDescriptionSnippet().length());
		assertLinkedInDate(j.getExpirationDate(), 2012, 2, 6);
		assertEquals(new Date(1328549338000l), j.getExpirationTimestamp());
		assertEquals(2160963, j.getId());
		assertProfile(j.getJobPoster(), "U1Gll9nr47", "Director, Allen Recruitment Consulting - Recruitment", "Louise", "Allen (Louise@AllenRec.com)",null, "");
		assertEquals("Dublin", j.getLocationDescription());
		assertLinkedInDate(j.getPostingDate(), 2011, 11, 8);
		assertEquals(new Date(1320773338000l), j.getPostingTimestamp());
		assertEquals("60 - 70K or 350-400/day (EURO)", j.getSalary());
		assertEquals("http://www.linkedin.com/jobs?viewJob=&jobId=2160963&trk=api*a151944*s160233*", j.getSiteJobUrl());
		assertEquals(494, j.getSkillsAndExperience().length());
		
		JobPosition p = j.getPosition();
		assertEquals("4", p.getExperienceLevel().getCode());
		assertEquals("Mid-Senior level", p.getExperienceLevel().getName());
		assertEquals("4", p.getIndustries().get(0).getCode());
		assertEquals("Computer Software", p.getIndustries().get(0).getName());
		assertEquals("it", p.getJobFunctions().get(0).getCode());
		assertEquals("Information Technology", p.getJobFunctions().get(0).getName());
		assertEquals("F", p.getJobType().getCode());
		assertEquals("Full-time", p.getJobType().getName());
		assertEquals("ie", p.getLocation().getCountry());
		assertEquals("Ireland", p.getLocation().getName());
		assertEquals("Java Developer - GWT (Perm or Contract) \u2013 Dublin, Ireland", p.getTitle());
	}
	
	@Test
	public void getSuggestions() {
		mockServer.expect(requestTo(JobTemplate.SUGGESTED_URL
				.replaceFirst("\\{\\&start\\}", "start=0")
				.replaceFirst("\\{\\&count\\}", "&count=10") + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("job_suggestions.json", getClass()), MediaType.APPLICATION_JSON));
		
		List<Job> jobs = linkedIn.jobOperations().getSuggestions(0, 10).getJobs();
		
		assertEquals(10, jobs.size());
		Job j = jobs.get(6);
		assertEquals(true, j.isActive());
		assertEquals(139355, j.getCompany().getId());
		assertEquals("Allen Recruitment Consulting", j.getCompany().getName());
		assertEquals("5404", j.getCustomerJobCode());
		assertEquals(2755, j.getDescription().length());
		assertEquals(400, j.getDescriptionSnippet().length());
		assertLinkedInDate(j.getExpirationDate(), 2012, 2, 6);
		assertEquals(new Date(1328549338000l), j.getExpirationTimestamp());
		assertEquals(2160963, j.getId());
		assertProfile(j.getJobPoster(), "U1Gll9nr47", "Director, Allen Recruitment Consulting - Recruitment", "Louise", "Allen (Louise@AllenRec.com)",null, "");
		assertEquals("Dublin", j.getLocationDescription());
		assertLinkedInDate(j.getPostingDate(), 2011, 11, 8);
		assertEquals(new Date(1320773338000l), j.getPostingTimestamp());
		assertEquals("60 - 70K or 350-400/day (EURO)", j.getSalary());
		assertEquals("http://www.linkedin.com/jobs?viewJob=&jobId=2160963&trk=api*a151944*s160233*", j.getSiteJobUrl());
		assertEquals(494, j.getSkillsAndExperience().length());
		
		JobPosition p = j.getPosition();
		assertEquals("4", p.getExperienceLevel().getCode());
		assertEquals("Mid-Senior level", p.getExperienceLevel().getName());
		assertEquals("4", p.getIndustries().get(0).getCode());
		assertEquals("Computer Software", p.getIndustries().get(0).getName());
		assertEquals("it", p.getJobFunctions().get(0).getCode());
		assertEquals("Information Technology", p.getJobFunctions().get(0).getName());
		assertEquals("F", p.getJobType().getCode());
		assertEquals("Full-time", p.getJobType().getName());
		assertEquals("ie", p.getLocation().getCountry());
		assertEquals("Ireland", p.getLocation().getName());
		assertEquals("Java Developer - GWT (Perm or Contract) \u2013 Dublin, Ireland", p.getTitle());
	}
	
	@Test
	public void getJob() {
		mockServer.expect(requestTo(JobTemplate.JOB_URL
				.replaceFirst("\\{id\\}", "2160963") + "?oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("job.json", getClass()), MediaType.APPLICATION_JSON));
		JobSearchParameters parameters = new JobSearchParameters();
		parameters.setCountryCode("ie");
		parameters.setKeywords("j2ee");
		
		Job j = linkedIn.jobOperations().getJob(2160963);
		
		assertEquals(true, j.isActive());
		assertEquals(139355, j.getCompany().getId());
		assertEquals("Allen Recruitment Consulting", j.getCompany().getName());
		assertEquals("5404", j.getCustomerJobCode());
		assertEquals(2755, j.getDescription().length());
		assertEquals(400, j.getDescriptionSnippet().length());
		assertLinkedInDate(j.getExpirationDate(), 2012, 2, 6);
		assertEquals(new Date(1328549338000l), j.getExpirationTimestamp());
		assertEquals(2160963, j.getId());
		assertProfile(j.getJobPoster(), "U1Gll9nr47", "Director, Allen Recruitment Consulting - Recruitment", "Louise", "Allen (Louise@AllenRec.com)",null, "");
		assertEquals("Dublin", j.getLocationDescription());
		assertLinkedInDate(j.getPostingDate(), 2011, 11, 8);
		assertEquals(new Date(1320773338000l), j.getPostingTimestamp());
		assertEquals("60 - 70K or 350-400/day (EURO)", j.getSalary());
		assertEquals("http://www.linkedin.com/jobs?viewJob=&jobId=2160963&trk=api*a151944*s160233*", j.getSiteJobUrl());
		assertEquals(494, j.getSkillsAndExperience().length());
		
		JobPosition p = j.getPosition();
		assertEquals("4", p.getExperienceLevel().getCode());
		assertEquals("Mid-Senior level", p.getExperienceLevel().getName());
		assertEquals("4", p.getIndustries().get(0).getCode());
		assertEquals("Computer Software", p.getIndustries().get(0).getName());
		assertEquals("it", p.getJobFunctions().get(0).getCode());
		assertEquals("Information Technology", p.getJobFunctions().get(0).getName());
		assertEquals("F", p.getJobType().getCode());
		assertEquals("Full-time", p.getJobType().getName());
		assertEquals("ie", p.getLocation().getCountry());
		assertEquals("Ireland", p.getLocation().getName());
		assertEquals("Java Developer - GWT (Perm or Contract) \u2013 Dublin, Ireland", p.getTitle());
	}
	
	@Test
	public void getBookmarks() {
		mockServer.expect(requestTo(JobTemplate.BOOKMARKS_URL
				.replaceFirst("\\{\\&start\\}", "start=0")
				.replaceFirst("\\{\\&count\\}", "&count=10") + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("job_bookmarks.json", getClass()), MediaType.APPLICATION_JSON));
		
		JobBookmarks r = linkedIn.jobOperations().getBookmarks(0, 10);
		assertEquals(0,r.getCount());
		assertEquals(0,r.getStart());
		assertEquals(1,r.getTotal());
		
		JobBookmark b = r.getJobBookmarks().get(0);
		assertEquals(false, b.isApplied());
		assertEquals(true, b.isSaved());
		assertEquals(new Date(1322150498000l), b.getSavedTimestamp());
		
		Job j = b.getJob();
		assertEquals(true, j.isActive());
		assertEquals(139355, j.getCompany().getId());
		assertEquals("Allen Recruitment Consulting", j.getCompany().getName());
		assertEquals(2160963, j.getId());
		assertEquals("Java Developer - GWT (Perm or Contract) \u2013 Dublin, Ireland", j.getPosition().getTitle());
		assertEquals(new Date(1320773338000l), j.getPostingTimestamp());
		assertEquals("Our Client has an excellent opportunity for a number Java GWT Developers. You will be based out of Dublin City Centre office. Overseas candidates are welcome to apply, but ideally you should be currently eligible to work in the EU / Ireland Responsibilities: Ownership of the design and the implementation (estimation, breakdown of tasks) for complex business functional specifications through the ", j.getDescriptionSnippet());
	}
	
	@Test
	public void bookmark() {
		mockServer.expect(requestTo(JobTemplate.BOOKMARK_URL + "?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("{\"job\":{\"id\":123456}}"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		linkedIn.jobOperations().bookmarkJob(123456);
	}
	
	@Test
	public void unbookmark() {
		mockServer.expect(requestTo(JobTemplate.UNBOOKMARK_URL.replaceFirst("\\{job-id\\}", "123456") + "?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(DELETE))
			.andExpect(content().string(""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		linkedIn.jobOperations().unbookmarkJob(123456);
	}

}
