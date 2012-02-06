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

/**
 * Position
 * 
 * @author Robert Drysdale
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
	
	public Position(Company company, String id, boolean isCurrent, LinkedInDate startDate, LinkedInDate endDate, String summary, String title) {
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
