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

import java.io.Serializable;

import org.springframework.social.linkedin.api.Group.MembershipState;

/**
 * Model class representing group settings on LinkedIn
 * 
 * @author Robert Drysdale
 */
public class GroupSettings extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Boolean allowMessagesFromMembers;
	
	private final Boolean emailAnnouncementsFromManagers;

	private final EmailDigestFrequency emailDigestFrequency;

	private final Boolean emailForEveryNewPost;

	private final Group group;

	private final MembershipState membershipState;

	private final Boolean showGroupLogoInProfile;

	public GroupSettings(Boolean allowMessagesFromMembers,
			Boolean emailAnnouncementsFromManagers,
			EmailDigestFrequency emailDigestFrequency, Boolean emailForEveryNewPost,
			Group group, MembershipState membershipState, Boolean showGroupLogoInProfile) {
		this.allowMessagesFromMembers = allowMessagesFromMembers;
		this.emailAnnouncementsFromManagers = emailAnnouncementsFromManagers;
		this.emailDigestFrequency = emailDigestFrequency;
		this.emailForEveryNewPost = emailForEveryNewPost;
		this.group = group;
		this.membershipState = membershipState;
		this.showGroupLogoInProfile = showGroupLogoInProfile;
	}
	
	public Boolean getAllowMessagesFromMembers() {
		return allowMessagesFromMembers;
	}
	
	public Boolean getEmailAnnouncementsFromManagers() {
		return emailAnnouncementsFromManagers;
	}
	
	public EmailDigestFrequency getEmailDigestFrequency() {
		return emailDigestFrequency;
	}
	
	public Boolean getEmailForEveryNewPost() {
		return emailForEveryNewPost;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public MembershipState getMembershipState() {
		return membershipState;
	}
	
	public Boolean getShowGroupLogoInProfile() {
		return showGroupLogoInProfile;
	}
	
	public static enum EmailDigestFrequency {
		NONE,
		DAILY,
		WEEKLY
	}

}
