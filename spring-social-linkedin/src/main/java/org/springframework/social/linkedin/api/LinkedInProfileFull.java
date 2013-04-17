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
package org.springframework.social.linkedin.api;

import java.util.List;


/**
 * Full LinkedInProfile returned by querying profile
 * 
 * @author Robert Drysdale
 */
public class LinkedInProfileFull extends LinkedInProfile {

	private static final long serialVersionUID = 1L;
	
	private List<Position> positions;
	
	private List<Position> threeCurrentPositions;
	
	private List<Position> threePastPositions;
	
	private List<Recommendation> recommendationsReceived;
	
	private String emailAddress;
	
	private List<ImAccount> imAccounts;
	
	private List<TwitterAccount> twitterAccounts;
	
	private List<UrlResource> memberUrlResources;
	
	private List<PhoneNumber> phoneNumbers;
	
	private List<String> skills;
	
	private List<String> languages;
	
	private List<Education> educations;
	
	private String proposalComments;
	
	private String specialties;
	
	private int numConnections;
	
	private boolean numConnectionsCapped;
	
	private int numRecommenders;
	
	private String mainAddress;
	
	private String associations;
	
	private Location location;
	
	private String interests;
	
	private String honors;
	
	private int distance;
	
	private LinkedInDate dateOfBirth;
	
	private CurrentShare currentShare;
	
	private Relation relationToViewer;

	public LinkedInProfileFull(String id, String firstName, String lastName,
			String headline, String industry, String publicProfileUrl,
			UrlResource siteStandardProfileRequest, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl,
				siteStandardProfileRequest, profilePictureUrl);
	}

	public List<Position> getPositions() {
		return positions;
	}

	public List<Position> getThreeCurrentPositions() {
		return threeCurrentPositions;
	}

	public List<Position> getThreePastPositions() {
		return threePastPositions;
	}

	public List<Recommendation> getRecommendationsReceived() {
		return recommendationsReceived;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public List<ImAccount> getImAccounts() {
		return imAccounts;
	}

	public List<TwitterAccount> getTwitterAccounts() {
		return twitterAccounts;
	}

	public List<UrlResource> getMemberUrlResources() {
		return memberUrlResources;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public List<String> getSkills() {
		return skills;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public String getProposalComments() {
		return proposalComments;
	}

	public String getSpecialties() {
		return specialties;
	}

	public int getNumConnections() {
		return numConnections;
	}

	public boolean isNumConnectionsCapped() {
		return numConnectionsCapped;
	}

	public int getNumRecommenders() {
		return numRecommenders;
	}

	public String getMainAddress() {
		return mainAddress;
	}

	public String getAssociations() {
		return associations;
	}

	public Location getLocation() {
		return location;
	}

	public String getInterests() {
		return interests;
	}

	public String getHonors() {
		return honors;
	}

	public int getDistance() {
		return distance;
	}

	public LinkedInDate getDateOfBirth() {
		return dateOfBirth;
	}

	public CurrentShare getCurrentShare() {
		return currentShare;
	}

	public Relation getRelationToViewer() {
		return relationToViewer;
	}

}
