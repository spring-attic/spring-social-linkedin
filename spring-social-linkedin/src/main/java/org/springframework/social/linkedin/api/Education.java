package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Education Details for a Profile on LinkedIn
 * 
 * @author Robert Drysdale
 *
 */
public class Education implements Serializable {
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
