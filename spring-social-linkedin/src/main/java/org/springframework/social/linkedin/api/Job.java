/*
 * Copyright 2012 the original author or authors.
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
package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Details of a Posted Job or Shared Job on LinkedIn
 * 
 * @author Robert Drysdale
 *
 */
public class Job implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final Company company;
	private final String description;
	private final int id;
	private final String locationDescription;
	private final UrlResource siteJobRequest;

	private boolean active;
	private String customerJobCode;
	private String descriptionSnippet;
	private LinkedInDate expirationDate;
	private Date expirationTimestamp;
	private LinkedInProfile jobPoster;
	private JobPosition position;
	private LinkedInDate postingDate;
	private Date postingTimestamp;
	private String salary;
	private String siteJobUrl;
	private String skillsAndExperience;
	
	public Job(Company company, String description, int id, String locationDescription, UrlResource siteJobRequest) {
		this.company = company;
		this.description = description;
		this.id = id;
		this.locationDescription = locationDescription;
		this.siteJobRequest = siteJobRequest;
	}

	public Company getCompany() {
		return company;
	}
	
	public String getDescriptionSnippet() {
		return descriptionSnippet;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public JobPosition getPosition() {
		return position;
	}

	public UrlResource getSiteJobRequest() {
		return siteJobRequest;
	}
	
	public LinkedInProfile getJobPoster() {
		return jobPoster;
	}

	public boolean isActive() {
		return active;
	}

	public LinkedInDate getExpirationDate() {
		return expirationDate;
	}

	public Date getExpirationTimestamp() {
		return expirationTimestamp;
	}

	public LinkedInDate getPostingDate() {
		return postingDate;
	}

	public Date getPostingTimestamp() {
		return postingTimestamp;
	}

	public String getSalary() {
		return salary;
	}

	public String getSiteJobUrl() {
		return siteJobUrl;
	}

	public String getSkillsAndExperience() {
		return skillsAndExperience;
	}
	
	public String getCustomerJobCode() {
		return customerJobCode;
	}
}
