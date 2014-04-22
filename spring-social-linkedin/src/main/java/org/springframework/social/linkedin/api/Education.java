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
package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Model class representing education details for a Profile on LinkedIn
 * 
 * @author Robert Drysdale
 */
public class Education extends LinkedInObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String activities;
	
	private final String degree;
	
	private String fieldOfStudy;
	
	private String id;
	
	private String notes;
	
	private String schoolName;
	
	private LinkedInDate startDate;
	
	private LinkedInDate endDate;

	public Education(String activities, String degree, String fieldOfStudy, String id, String notes,
			String schoolName, LinkedInDate startDate, LinkedInDate endDate) {
		this.activities = activities;
		this.degree = degree;
		this.fieldOfStudy = fieldOfStudy;
		this.id = id;
		this.notes = notes;
		this.schoolName = schoolName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getActivities() {
		return activities;
	}

	public String getDegree() {
		return degree;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public String getId() {
		return id;
	}

	public String getNotes() {
		return notes;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public LinkedInDate getStartDate() {
		return startDate;
	}

	public LinkedInDate getEndDate() {
		return endDate;
	}

}
