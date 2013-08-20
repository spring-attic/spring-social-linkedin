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

import java.io.Serializable;
import java.util.List;

import org.springframework.social.linkedin.api.Post.PostCategory;

/**
 * Model class representing a group on LinkedIn
 * 
 * @author Robert Drysdale
 */
public class Group extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean allowMemberInvites;
	private GroupCategory category;
	private List<GroupCount> countsByCategory;
	private String description;
	private final Integer id;
	private Boolean isOpenToNonMembers;
	private String largeLogoUrl;
	private String locale;
	private final String name;
	private GroupPosts posts;
	private GroupRelation relationToViewer;
	private String shortDescription;
	private String siteGroupUrl;
	private String smallLogoUrl;
	private String websiteUrl;
	
	public Group(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Boolean getAllowMemberInvites() {
		return allowMemberInvites;
	}
	
	public GroupCategory getCategory() {
		return category;
	}
	
	public List<GroupCount> getCountsByCategory() {
		return countsByCategory;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Boolean isOpenToNonMembers() {
		return isOpenToNonMembers;
	}
	
	public String getLargeLogoUrl() {
		return largeLogoUrl;
	}
	
	public String getLocale() {
		return locale;
	}
	
	public String getName() {
		return name;
	}
	
	public GroupPosts getPosts() {
		return posts;
	}
	
	public GroupRelation getRelationToViewer() {
		return relationToViewer;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getSiteGroupUrl() {
		return siteGroupUrl;
	}
	
	public String getSmallLogoUrl() {
		return smallLogoUrl;
	}
	
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	
	public static class GroupCount extends LinkedInObject implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private final PostCategory category;
		private final Integer count; 
		
		public GroupCount(PostCategory category, Integer count) {
			this.category = category;
			this.count = count;
		}
		
		public PostCategory getCategory() {
			return category;
		}
		public Integer getCount(){
			return count; 
		}
	}
	
	public static class GroupPosts extends SearchResult {

		private static final long serialVersionUID = 1L;

		public GroupPosts(int count, int start, int total) {
			super(count, start, total);
		}
		
		private List<Post> posts;
		
		public List<Post> getPosts() {
			return posts;
		}
		
	}
	
	public static class GroupRelation extends LinkedInObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final List<GroupAvailableAction> availableActions;
		private final MembershipState membershipState;
		
		public GroupRelation(List<GroupAvailableAction> availableActions,
				MembershipState membershipState) {
			this.availableActions = availableActions;
			this.membershipState = membershipState;
		}
		
		public List<GroupAvailableAction> getAvailableActions() {
			return availableActions;
		}
		public MembershipState getMembershipState() {
			return membershipState;
		}
	}
	
	public static enum GroupCategory {
		ALUMNI,
		CORPORATE,
		CONFERENCE,
		NETWORK,
		PHILANTHROPIC,
		PROFESSIONAL,
		OTHER
	}
	
	public static enum MembershipState {
		BLOCKED,
		NON_MEMBER,
		AWAITING_CONFIRMATION,
		AWAITING_PARENT_GROUP_CONFIRMATION,
		MEMBER,
		MODERATOR,
		MANAGER,
		OWNER
	}
	
	public static enum GroupAvailableAction {
		ADD_POST,
		LEAVE,
		VIEW_POSTS
	}
}
