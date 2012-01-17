package org.springframework.social.linkedin.api;

import java.io.Serializable;

import org.springframework.social.linkedin.api.Group.MembershipState;

public class GroupSettings implements Serializable {
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
