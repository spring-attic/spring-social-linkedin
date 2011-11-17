package org.springframework.social.linkedin.api;

public class LinkedInDate {
	private final int year;
	private final int month;
	private final int day;
	
	public LinkedInDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
}
