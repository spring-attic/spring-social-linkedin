/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.api;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Profile search parameters
 * 
 * @author Robert Drysdale
 */
public class SearchParameters {
	
	private String keywords;

	private String firstName;
	
	private String lastName;
	
	private String companyName;
	
	private Boolean currentCompany;
	
	private String title;
	
	private Boolean currentTitle;
	
	private String schoolName;
	
	private Boolean currentSchool;
	
	private String countryCode;
	
	private String postalCode;
	
	private Integer distance;
	
	private int start;
	
	private int count = 10;
	
	private Sort sort;

    private MultiValueMap<FacetType, String> facets = new LinkedMultiValueMap<FacetType, String>();
	
	public SearchParameters() {}
	
	public SearchParameters(String keywords, String firstName, String lastName, String companyName, Boolean currentCompany, String title, Boolean currentTitle, 
			String schoolName, Boolean currentSchool, String countryCode, String postalCode, Integer distance, int start, int count, Sort sort) {
		super();
		this.keywords = keywords;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.currentCompany = currentCompany;
		this.title = title;
		this.currentTitle = currentTitle;
		this.schoolName = schoolName;
		this.currentSchool = currentSchool;
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
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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
	
	public Boolean getCurrentCompany() {
		return currentCompany;
	}
	
	public void setCurrentCompany(Boolean currentCompany) {
		this.currentCompany = currentCompany;
	}
	
	public Boolean getCurrentSchool() {
		return currentSchool;
	}
	
	public void setCurrentSchool(Boolean currentSchool) {
		this.currentSchool = currentSchool;
	}
	
	public Boolean getCurrentTitle() {
		return currentTitle;
	}
	
	public void setCurrentTitle(Boolean currentTitle) {
		this.currentTitle = currentTitle;
	}
	
	public Sort getSort() {
		return sort;
	}
	
	public void setSort(Sort sort) {
		this.sort = sort;
	}

    public void addFacet(FacetType facetType, String facetValue) {
        this.facets.add(facetType, facetValue);
    }

    public MultiValueMap<FacetType, String> getFacets() {
        return this.facets;
    }

	public static enum Sort {
		CONNECTIONS,
		RECOMMENDERS,
		DISTANCE,
		RELEVANCE;
		
		public String toString() {
			return this.name().toLowerCase();
		}
	}

	public static enum FacetType {
		LOCATION("location"),
		INDUSTRY("industry"),
		NETWORK("network"),
		LANGUAGE("language"),
		CURRENT_COMPANY("current-company"),
		PAST_COMPANY("past-company"),
		SCHOOL("school");

		private String facetType;

		private FacetType(String facetType) {
			this.facetType = facetType;
		}

		@Override
		public String toString() {
			return this.facetType;
		}
	}

	public static final String NETWORK_FIRST_DEGREE = "F";
	public static final String NETWORK_SECOND_DEGREE = "S";
	public static final String NETWORK_INSIDE_GROUPS = "A";
	public static final String NETWORK_OUT_OF_NETWORK = "O";
	
	public static final String LANGUAGE_ENGLISH = "en";
	public static final String LANGUAGE_SPANISH = "es";
	public static final String LANGUAGE_FRENCH = "fr";
	public static final String LANGUAGE_GERMAN = "de";
	public static final String LANGUAGE_ITALIAN = "it";
	public static final String LANGUAGE_PORTUGUESE = "pt";
	public static final String LANGUAGE_OTHERS = "_o";

}
