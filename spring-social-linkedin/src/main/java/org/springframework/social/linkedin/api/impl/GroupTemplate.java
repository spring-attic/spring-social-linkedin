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
package org.springframework.social.linkedin.api.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.social.linkedin.api.Group;
import org.springframework.social.linkedin.api.Group.GroupPosts;
import org.springframework.social.linkedin.api.GroupMemberships;
import org.springframework.social.linkedin.api.GroupOperations;
import org.springframework.social.linkedin.api.GroupSuggestions;
import org.springframework.social.linkedin.api.PostComments;
import org.springframework.web.client.RestOperations;

class GroupTemplate extends AbstractTemplate implements GroupOperations {
	
	private final RestOperations restOperations;
	
	public GroupTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public Group getGroupDetails(Integer id) {
		return restOperations.getForObject(GROUP_DETAILS_URL, Group.class, id);
	}
	
	public GroupMemberships getGroupMemberships() {
		return restOperations.getForObject(GROUP_MEMBERSHIPS_URL, GroupMemberships.class);
	}
	
	public GroupMemberships getGroupMemberships(int start, int count) {
		return restOperations.getForObject(GROUP_MEMBERSHIPS_URL + "?start=" + start + "&count=" + count, 
				GroupMemberships.class);
	}
	
	public GroupSuggestions getGroupSuggestions() {
		return restOperations.getForObject(GROUP_SUGGESTIONS_URL, GroupSuggestions.class);
	}
	
	public GroupSuggestions getGroupSuggestions(int start, int count) {
		return restOperations.getForObject(GROUP_SUGGESTIONS_URL + "?start=" + start + "&count=" + count, 
				GroupSuggestions.class);
	}
	
	public GroupPosts getPosts(Integer groupId) {
		return restOperations.getForObject(GROUP_POSTS_URL, GroupPosts.class, groupId);
	}
	
	public GroupPosts getPosts(Integer groupId, int start, int count) {
		return restOperations.getForObject(GROUP_POSTS_URL + "&start=" + start + "&count=" + count, 
				GroupPosts.class, groupId);
	}
	
	public PostComments getPostComments(String postId) {
		return restOperations.getForObject(GROUP_POST_COMMENTS_URL, PostComments.class, postId);
	}
	
	public PostComments getPostComments(String postId, int start, int count) {
		return restOperations.getForObject(GROUP_POST_COMMENTS_URL + "?start=" + start + "&count=" + count, 
				PostComments.class, postId);
	}
	
	public void joinGroup(Integer groupId) {
		restOperations.put(GROUP_JOIN_LEAVE_URL, 
				Collections.singletonMap("membership-state", 
						Collections.singletonMap("code", "member")),
						groupId);
	}
	
	public void leaveGroup(Integer groupId) {
		restOperations.delete(GROUP_JOIN_LEAVE_URL, groupId);
	}
	
	public void createPost(Integer groupId, String title, String summary) {
		Map<String, String> post = new HashMap<String,String>();
		post.put("title", title);
		post.put("summary", summary);
		restOperations.postForObject(GROUP_CREATE_POST_URL, post, String.class, groupId);
	}
	
	public void likePost(String postId) {
		restOperations.put(GROUP_POST_LIKE_URL, "true",
						postId);
	}
	
	public void unlikePost(String postId) {
		restOperations.put(GROUP_POST_LIKE_URL, "false",
						postId);
	}
	
	public void followPost(String postId) {
		restOperations.put(GROUP_POST_FOLLOW_URL, "true",
						postId);
	}
	
	public void unfollowPost(String postId) {
		restOperations.put(GROUP_POST_FOLLOW_URL, "false",
						postId);
	}
	
	public void flagPostAsJob(String postId) {
		restOperations.put(GROUP_POST_FLAG_URL, "\"job\"",
				postId);
	}
	
	public void flagPostAsPromotion(String postId) {
		restOperations.put(GROUP_POST_FLAG_URL, "\"promotion\"",
				postId);
	}
	
	public void deleteOrFlagPostAsInappropriate(String postId) {
		restOperations.delete(GROUP_POST_DELETE_URL, postId);
	}
	
	public void addCommentToPost(String postId, String text) {
		restOperations.postForObject(GROUP_POST_ADD_COMMENT_URL, 
				Collections.singletonMap("text",text), String.class, postId);
	}
	
	public void deleteOrFlagCommentAsInappropriate(String commentId) {
		restOperations.delete(GROUP_POST_DELETE_COMMENT_URL, commentId);
	}
	
	public void deleteGroupSuggestion(Integer groupId) {
		restOperations.delete(GROUP_SUGGESTION_DELETE_URL, groupId);
	}
	
	public static final String BASE_URL = "https://api.linkedin.com/v1/";
	public static final String BASE_PEOPLE_URL = BASE_URL + "people/";
	public static final String GROUP_BASE_URL = BASE_URL + "groups/";
	public static final String GROUP_POSTS_BASE_URL = BASE_URL + "posts/";
	public static final String GROUP_DETAILS_URL = GROUP_BASE_URL + "{group-id}:(id,name,short-description,description,relation-to-viewer:(membership-state,available-actions),posts,counts-by-category,is-open-to-non-members,category,website-url,locale,location:(country,postal-code),allow-member-invites,site-group-url,small-logo-url,large-logo-url)";
	public static final String GROUP_JOIN_LEAVE_URL = BASE_PEOPLE_URL + "~/group-memberships/{group-id}";
	public static final String GROUP_MEMBERSHIPS_URL = BASE_PEOPLE_URL + "~/group-memberships:(group:(id,name),membership-state,show-group-logo-in-profile,allow-messages-from-members,email-digest-frequency,email-announcements-from-managers,email-for-every-new-post)";
	public static final String GROUP_SUGGESTIONS_URL = BASE_PEOPLE_URL + "~/suggestions/groups:(id,name,is-open-to-non-members)";
	public static final String GROUP_SUGGESTION_DELETE_URL = BASE_PEOPLE_URL + "~/suggestions/groups/{id}";
	public static final String GROUP_POSTS_URL = GROUP_BASE_URL + "{group-id}/posts:(id,creation-timestamp,title,summary,creator:(first-name,last-name,picture-url,headline),likes,attachment:(image-url,content-domain,content-url,title,summary),relation-to-viewer)?order=recency";
	public static final String GROUP_POST_COMMENTS_URL = GROUP_POSTS_BASE_URL + "{post-id}/comments:(creator:(first-name,last-name,picture-url),creation-timestamp,id,text)";
	public static final String GROUP_CREATE_POST_URL = GROUP_BASE_URL + "{group-id}/posts";
	public static final String GROUP_POST_LIKE_URL = GROUP_POSTS_BASE_URL + "{post-id}/relation-to-viewer/is-liked";
	public static final String GROUP_POST_FOLLOW_URL = GROUP_POSTS_BASE_URL + "{post-id}/relation-to-viewer/is-following";
	public static final String GROUP_POST_FLAG_URL = GROUP_POSTS_BASE_URL + "{post-id}/category/code";
	public static final String GROUP_POST_DELETE_URL = GROUP_POSTS_BASE_URL + "{post-id}";
	public static final String GROUP_POST_ADD_COMMENT_URL = GROUP_POSTS_BASE_URL + "{post-id}/comments";
	public static final String GROUP_POST_DELETE_COMMENT_URL = BASE_URL + "comments/{comment-id}";

}
