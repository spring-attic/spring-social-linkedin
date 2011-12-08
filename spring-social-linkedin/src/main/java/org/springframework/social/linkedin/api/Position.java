package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Position
 * 
 * @author Robert Drysdale
 *
 */
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Company company;
	private final String id;
	private final boolean isCurrent;
	private final LinkedInDate startDate;
	private final LinkedInDate endDate;
	private final String summary;
	private final String title;
	
	public Position(Company company, String id, boolean isCurrent,
			LinkedInDate startDate, LinkedInDate endDate, String summary, String title) {
		this.company = company;
		this.id = id;
		this.isCurrent = isCurrent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		this.title = title;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean getIsCurrent() {
		return isCurrent;
	}
	
	public LinkedInDate getStartDate() {
		return startDate;
	}
	
	public LinkedInDate getEndDate() {
		return endDate;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String getTitle() {
		return title;
	}
	
	public static final class StartDate {
		private final int month;
		private final int year;
		
		public StartDate(int month, int year) {
			this.month = month;
			this.year = year;
		}
		
		public int getMonth() {
			return month;
		}
		
		public int getYear() {
			return year;
		}
	}
	
}
