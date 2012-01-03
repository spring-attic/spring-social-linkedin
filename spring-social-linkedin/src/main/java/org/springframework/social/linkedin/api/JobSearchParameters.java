package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Search parameters to search for jobs
 * Leave parameters as null to turn off
 * 
 * @author Robert Drysdale
 *
 */
public class JobSearchParameters implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String keywords;
	private String companyName;
	private String jobTitle;
	private String countryCode;
	private String postalCode;
	private Integer distance;
	private int start;
	private int count = 10;
	private JobSearchSort sort;
	
	public JobSearchParameters() {}

	public JobSearchParameters(String keywords, String companyName,
			String jobTitle, String countryCode, String postalCode,
			Integer distance, int start, int count, JobSearchSort sort) {
		super();
		this.keywords = keywords;
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.countryCode = countryCode;
		this.postalCode = postalCode;
		this.distance = distance;
		this.start = start;
		this.count = count;
		this.sort = sort;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public JobSearchSort getSort() {
		return sort;
	}

	public void setSort(JobSearchSort sort) {
		this.sort = sort;
	}
	
	public static enum JobSearchSort {
		R,
		DA,
		DD
	}
}
