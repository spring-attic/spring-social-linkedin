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

import org.springframework.social.linkedin.api.Group.GroupPosts;

/**
 * Operations on LinkedIn Group API
 * 
 * @author Robert Drysdale
 */
public interface GroupOperations {
	
	/**
	 * Get Details for a Group
	 * @param id Id of Group
	 * @return Group
	 */
	Group getGroupDetails( Integer id );
	
	/**
	 * Get List of Groups a User is a member of
	 * 
	 * @return List of Group Memberships
	 */
	GroupMemberships getGroupMemberships();
	
	/**
	 * Get List of Groups a User is a member of
	 * 
	 * @param start First Group to return
	 * @param count Number of Groups to return
	 * @return List of Group Memberships
	 */
	GroupMemberships getGroupMemberships(int start, int count);
	
	/**
	 * Get List of Group Suggestions for a User
	 * 
	 * @return List of Group Suggestions
	 */
	GroupSuggestions getGroupSuggestions();
	
	/**
	 * Get List of Group Suggestions for a User
	 * 
	 * @param start First Group to return
	 * @param count Number of Groups to return
	 * @return List of Group Suggestions
	 */
	GroupSuggestions getGroupSuggestions(int start, int count);
	
	/**
	 * Join a Group
	 * @param groupId Id of Group
	 */
	void joinGroup(Integer groupId);
	
	/**
	 * Leave a Group
	 * @param groupId Id of Group
	 */
	void leaveGroup(Integer groupId);
	
	/**
	 * Get List of Posts for a group in time order
	 * 
	 * @param groupId Id of Group
	 * @return List of Posts
	 */
	GroupPosts getPosts(Integer groupId);
	
	/**
	 * Get List of Posts for a group in time order
	 * 
	 * @param groupId Id of Group
	 * @param start First Post to return
	 * @param count Number of Posts to return
	 * @return List of Posts
	 */
	GroupPosts getPosts(Integer groupId, int start, int count);
	
	/**
	 * Get List of Comments on a Post
	 * 
	 * @param postId Id of Post
	 * @return List of Comments
	 */
	PostComments getPostComments(String postId);
	
	/**
	 * Get List of Comments on a Post
	 * 
	 * @param postId Id of Post
	 * @param start First Post to return
	 * @param count Number of Posts to return
	 * @return List of Comments
	 */
	PostComments getPostComments(String postId, int start, int count);
	
	/**
	 * Create a Post
	 * 
	 * @param groupId Group to Create Post on
	 * @param title Title of Post
	 * @param summary Text of Post
	 */
	public void createPost(Integer groupId, String title, String summary);
	
	/**
	 * Like a Post
	 * 
	 * @param postId Id of Post
	 */
	public void likePost(String postId);
	
	/**
	 * Unlike a Post 
	 * @param postId Id of Post
	 */
	public void unlikePost(String postId);
	
	/**
	 * Follow a Post
	 * 
	 * @param postId Id of Post
	 */
	public void followPost(String postId);
	
	/**
	 * Like a Post
	 * 
	 * @param postId Id of Post
	 */
	public void unfollowPost(String postId);
	
	/**
	 * Flag a Post as a Job
	 * 
	 * @param postId Id of Post
	 */
	public void flagPostAsJob(String postId);
	
	/**
	 * Flag a Post as a Promotion
	 * 
	 * @param postId Id of Post
	 */
	public void flagPostAsPromotion(String postId);
	
	/**
	 * Delete a Post (if group administrator) or flag as inappropriate 
	 * 
	 * @param postId Id of Post
	 */
	public void deleteOrFlagPostAsInappropriate(String postId);
	
	/**
	 * Add a Comment to a Post
	 * 
	 * @param postId Id of Post
	 * @param text Text of Comment
	 */
	public void addCommentToPost(String postId, String text);
	
	/**
	 * Delete a Comment (if group administrator) or flag as inappropriate 
	 * 
	 * @param commentId Id of Comment
	 */
	public void deleteOrFlagCommentAsInappropriate(String commentId);
	
	/**
	 * Delete Group Suggestion
	 * 
	 * @param groupId Id of Group
	 */
	public void deleteGroupSuggestion(Integer groupId);

}
