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
package org.springframework.social.linkedin.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.Group;
import org.springframework.social.linkedin.api.Group.GroupAvailableAction;
import org.springframework.social.linkedin.api.Group.GroupCategory;
import org.springframework.social.linkedin.api.Group.GroupPosts;
import org.springframework.social.linkedin.api.Group.MembershipState;
import org.springframework.social.linkedin.api.GroupMemberships;
import org.springframework.social.linkedin.api.GroupSettings;
import org.springframework.social.linkedin.api.GroupSettings.EmailDigestFrequency;
import org.springframework.social.linkedin.api.GroupSuggestions;
import org.springframework.social.linkedin.api.Post;
import org.springframework.social.linkedin.api.Post.PostAvailableAction;
import org.springframework.social.linkedin.api.Post.PostCategory;
import org.springframework.social.linkedin.api.Post.PostType;
import org.springframework.social.linkedin.api.PostComment;
import org.springframework.social.linkedin.api.PostComments;

public class GroupTemplateTest extends AbstractLinkedInApiTest {
	@Test
	public void getGroupMemberships() {
		mockServer.expect(requestTo(GroupTemplate.GROUP_MEMBERSHIPS_URL + "?oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("group_memberships.json", getClass()), MediaType.APPLICATION_JSON));
		GroupMemberships memberships = linkedIn.groupOperations().getGroupMemberships();
		
		assertEquals(10, memberships.getCount());
		assertEquals(0, memberships.getStart());
		assertEquals(30, memberships.getTotal());
		assertEquals(10, memberships.getMemberships().size());
		
		GroupSettings s = memberships.getMemberships().get(0);
		assertEquals(true, s.getAllowMessagesFromMembers());
		assertEquals(false, s.getEmailAnnouncementsFromManagers());
		assertEquals(EmailDigestFrequency.NONE, s.getEmailDigestFrequency());
		assertEquals(MembershipState.MEMBER, s.getMembershipState());
		assertEquals(true, s.getShowGroupLogoInProfile());
		assertEquals(new Integer(69286), s.getGroup().getId());
		assertEquals("Software Architect Network", s.getGroup().getName());
	}
	
	@Test
	public void getGroupSuggestions() {
		mockServer.expect(requestTo(GroupTemplate.GROUP_SUGGESTIONS_URL + "?oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("group_suggestions.json", getClass()), MediaType.APPLICATION_JSON));
		GroupSuggestions suggestions = linkedIn.groupOperations().getGroupSuggestions();
		
		assertEquals(10, suggestions.getCount());
		assertEquals(0, suggestions.getStart());
		assertEquals(37, suggestions.getTotal());
		assertEquals(10, suggestions.getSuggestions().size());
		
		Group g = suggestions.getSuggestions().get(0);
		assertEquals(new Integer(1835657), g.getId());
		assertEquals(true, g.isOpenToNonMembers());
		assertEquals("97 Things Every Software Architect Should Know", g.getName());
	}
	
	@Test
	public void getGroupDetails() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_DETAILS_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "46964"))).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("group.json", getClass()), MediaType.APPLICATION_JSON));
		Group group = linkedIn.groupOperations().getGroupDetails(46964);

		assertEquals(true, group.getAllowMemberInvites());
		assertEquals(GroupCategory.PROFESSIONAL, group.getCategory());
		assertEquals(2, group.getCountsByCategory().size());
		
		assertEquals(PostCategory.DISCUSSION, group.getCountsByCategory().get(0).getCategory());
		assertEquals(new Integer(11), group.getCountsByCategory().get(0).getCount());
		assertEquals(PostCategory.JOB, group.getCountsByCategory().get(1).getCategory());
		assertEquals(new Integer(2), group.getCountsByCategory().get(1).getCount());
		
		assertEquals("This is a group for users of the Spring Framework and related projects (Roo, Batch, Integration, BlazeDS, Security, Web Flow etc) to meet and discuss things of common interest.", group.getDescription());
		assertEquals(new Integer(46964), group.getId());
		assertEquals(true, group.isOpenToNonMembers());
		assertEquals("https://media.linkedin.com/mpr/mpr/p/3/000/05d/2b8/0c75024.png", group.getLargeLogoUrl());
		assertEquals("en_US", group.getLocale());
		assertEquals("Spring Users", group.getName());
		assertEquals(10, group.getPosts().getPosts().size());
		assertEquals(0, group.getPosts().getStart());
		assertEquals(10, group.getPosts().getCount());
		assertEquals(199, group.getPosts().getTotal());
		
		assertProfile(group.getPosts().getPosts().get(0).getCreator(),
				"0_Fb-UVig_",
				"Business Development Executive at HOSTINGINDIA.CO",
				"Mukesh",
				"K.",
				null,
				null);
		
		assertEquals("g-46964-S-88566811", group.getPosts().getPosts().get(0).getId());
		assertEquals("Tips on Choosing The Best Web Hosting Services", group.getPosts().getPosts().get(0).getTitle());
		assertEquals(PostType.STANDARD, group.getPosts().getPosts().get(0).getType());
		
		assertEquals(3, group.getRelationToViewer().getAvailableActions().size());
		assertEquals(GroupAvailableAction.ADD_POST, group.getRelationToViewer().getAvailableActions().get(0));
		assertEquals(GroupAvailableAction.LEAVE, group.getRelationToViewer().getAvailableActions().get(1));
		assertEquals(GroupAvailableAction.VIEW_POSTS, group.getRelationToViewer().getAvailableActions().get(2));
		assertEquals(MembershipState.MEMBER, group.getRelationToViewer().getMembershipState());
		
		assertEquals("This is a group that welcomes all users of the Spring platform, including Spring Framework, Roo, Batch, Integration, BlazeDS, Security, Web Flow etc.", group.getShortDescription());
		assertEquals("https://www.linkedin.com/groups?gid=46964&trk=api*a151944*s160233*", group.getSiteGroupUrl());
		assertEquals("https://media.linkedin.com/mpr/mpr/p/2/000/05d/2b8/0cc68d3.png", group.getSmallLogoUrl());
		assertEquals("https://www.springsource.org", group.getWebsiteUrl());
	}
	
	@Test
	public void getPosts() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POSTS_URL + "&oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "46964"))).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("group_posts.json", getClass()), MediaType.APPLICATION_JSON));
		GroupPosts posts = linkedIn.groupOperations().getPosts(46964);
		
		assertEquals(10, posts.getCount());
		assertEquals(0, posts.getStart());
		assertEquals(199, posts.getTotal());
		assertEquals(10, posts.getPosts().size());
		
		Post p = posts.getPosts().get(0);
		
		assertEquals(new Date(1326384086809l), p.getCreationTimestamp());
		assertProfile(p.getCreator(), null, "Programmer analyst trainee at Cognizant Technology Solutions", "Sathish", "K.", null, null);
		
		assertEquals(2, p.getLikes().size());
		assertProfile(p.getLikes().get(0), "i21Fzhxxxx", "Managing Director", "Tom", "Smith", null, null);
		
		assertEquals("g-35936-S-88778673", p.getId());
		
		assertEquals(false, p.getRelationToViewer().isFollowing());
		assertEquals(false, p.getRelationToViewer().isLiked());
		assertEquals(7, p.getRelationToViewer().getAvailableActions().size());
		assertEquals(PostAvailableAction.ADD_COMMENT, p.getRelationToViewer().getAvailableActions().get(0));
		assertEquals(PostAvailableAction.CATEGORIZE_AS_JOB, p.getRelationToViewer().getAvailableActions().get(1));
		
		assertEquals("", p.getSummary());
		assertEquals("how to integate spring batch admin console into existing spring batch process application /web application.", p.getTitle());
		
		p = posts.getPosts().get(1);
		
		assertEquals(new Date(1326376683000l), p.getCreationTimestamp());
		assertProfile(p.getCreator(), null, "Contributor at Interns Over 40", "Namita", "M.", null, null);
		
		assertEquals(0, p.getLikes().size());
		
		assertEquals("g-35936-S-88778673", p.getId());
		
		assertEquals(false, p.getRelationToViewer().isFollowing());
		assertEquals(false, p.getRelationToViewer().isLiked());
		assertEquals(7, p.getRelationToViewer().getAvailableActions().size());
		assertEquals(PostAvailableAction.ADD_COMMENT, p.getRelationToViewer().getAvailableActions().get(0));
		assertEquals(PostAvailableAction.CATEGORIZE_AS_JOB, p.getRelationToViewer().getAvailableActions().get(1));
		
		assertEquals("We keep doing it the same way. Looking for jobs where everyone else is looking . How stupid can I be. Thanks Peggy. Join our Insider updates: https://brt89802.infusionsoft.com/app/form/newsletter", p.getSummary());
		assertEquals("\"How To Find Jobs in ANY Economy\"  For Older Skilled Workers.", p.getTitle());
		
		assertEquals("internsover40beta.blogspot.com", p.getAttachment().getContentDomain());
		assertEquals("https://internsover40beta.blogspot.com/2011/11/find-those-hidden-jobs-webinar.html#", p.getAttachment().getContentUrl());
		assertEquals("https://2.bp.blogspot.com/-rqUKPf90Tv8/TsboRglXxJI/AAAAAAAADe0/EafW1pC6sns/s72-c/c419333_m.jpg", p.getAttachment().getImageUrl());
		assertEquals("We keep doing it the same way. Looking for jobs where everyone else is looking . How stupid can I be. Thanks Peggy. Join our Insider updates: https://brt89802.infusionsoft.com/app/form/newsletter", p.getAttachment().getSummary());
		assertEquals("\"How To Find Jobs in ANY Economy\"  For Older Skilled Workers.", p.getAttachment().getTitle());
	
	}
	
	@Test
	public void getPostComments() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_COMMENTS_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-46964-S-87679641"))).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("group_post_comments.json", getClass()), MediaType.APPLICATION_JSON));
		
		PostComments comments = linkedIn.groupOperations().getPostComments("g-46964-S-87679641");
		
		assertEquals(5, comments.getCount());
		assertEquals(0, comments.getStart());
		assertEquals(10, comments.getTotal());
		assertEquals(5, comments.getComments().size());
		
		PostComment c = comments.getComments().get(0);
		assertEquals("g-4253322-S-89528249-64901084", c.getId());
		assertEquals(new Date(1325749391000l), c.getCreationTimestamp());
		assertProfile(c.getCreator(), null, null, "Piotr", "H.", null, null);
		assertEquals("https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/html/overview.html#overview-dependency-injection", c.getText());
		
		c = comments.getComments().get(1);
		assertEquals("g-4253322-S-89528249-64901084", c.getId());
		assertEquals(new Date(1325749913000l), c.getCreationTimestamp());
		assertProfile(c.getCreator(), null, null, "Gaurav", "D.", null, null);
		assertEquals("Thanks Piotr.", c.getText());
		
	}
	
	@Test
	public void createPost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_CREATE_POST_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "4253322")))
			.andExpect(method(POST))
			.andExpect(content().string("{\"title\":\"Test Post\",\"summary\":\"This is a test\"}"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		linkedIn.groupOperations().createPost(4253322, "Test Post", "This is a test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPostWithNullTitle() {
		linkedIn.groupOperations().createPost(4253322, null, "This is a test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPostWithEmptyTitle() {
		linkedIn.groupOperations().createPost(4253322, "", "This is a test");
	}

	@Test
	public void createPostWithNullSummary() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_CREATE_POST_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "4253322")))
				.andExpect(method(POST))
				.andExpect(content().string("{\"title\":\"Test Post\",\"summary\":\"\"}"))
				.andRespond(withSuccess("", MediaType.APPLICATION_JSON));

		linkedIn.groupOperations().createPost(4253322, "Test Post", null);
	}

	@Test
	public void createPostWithContent() {
		String jsonPayload = "{\"title\":\"Test Post\",\"summary\":\"This is a test\",\"content\":{\"title\":\"Content title\"," +
				"\"submittedUrl\":\"Submitted URL\",\"submittedImageUrl\":\"SubmittedImageUrl\",\"description\":\"Description\"}}";
		mockServer.expect(requestTo((GroupTemplate.GROUP_CREATE_POST_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "4253322")))
				.andExpect(method(POST))
				.andExpect(content().string(jsonPayload))
				.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		linkedIn.groupOperations().createPost(4253322, "Test Post", "This is a test", "Content title", "Submitted URL", "SubmittedImageUrl", "Description");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPostWithContentAndNullSubmittedUrl() {
		linkedIn.groupOperations().createPost(4253322, "Test Post", "This is a test", "Content title", null, "SubmittedImageUrl", "Description");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPostWithContentAndEmptySubmittedUrl() {
		linkedIn.groupOperations().createPost(4253322, "Test Post", "This is a test", "Content title", "", "SubmittedImageUrl", "Description");
	}

	@Test
	public void createPostWithContentAndNullTitle() {
		String jsonPayload = "{\"title\":\"Test Post\",\"summary\":\"This is a test\",\"content\":{\"title\":\"\"," +
				"\"submittedUrl\":\"Submitted URL\",\"submittedImageUrl\":\"SubmittedImageUrl\",\"description\":\"Description\"}}";
		mockServer.expect(requestTo((GroupTemplate.GROUP_CREATE_POST_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "4253322")))
				.andExpect(method(POST))
				.andExpect(content().string(jsonPayload))
				.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		linkedIn.groupOperations().createPost(4253322, "Test Post", "This is a test", null, "Submitted URL", "SubmittedImageUrl", "Description");
	}

	@Test
	public void likePost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_LIKE_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(PUT))
			.andExpect(content().string("true"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().likePost("g-4253322-S-89528249");
	}
	
	@Test
	public void unlikePost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_LIKE_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(PUT))
			.andExpect(content().string("false"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().unlikePost("g-4253322-S-89528249");
	}
	
	@Test
	public void flagPostAsJob() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_FLAG_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(PUT))
			.andExpect(content().string("\"job\""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().flagPostAsJob("g-4253322-S-89528249");
	}
	
	@Test
	public void flagPostAsPromotion() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_FLAG_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(PUT))
			.andExpect(content().string("\"promotion\""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().flagPostAsPromotion("g-4253322-S-89528249");
	}
	
	@Test
	public void followPost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_FOLLOW_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(PUT))
			.andExpect(content().string("true"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().followPost("g-4253322-S-89528249");
	}
	
	@Test
	public void unfollowPost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_FOLLOW_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(PUT))
			.andExpect(content().string("false"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().unfollowPost("g-4253322-S-89528249");
	}
	
	@Test
	public void deletePost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_DELETE_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(DELETE))
			.andExpect(content().string(""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().deleteOrFlagPostAsInappropriate("g-4253322-S-89528249");
	}
	
	@Test
	public void addCommentToPost() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_ADD_COMMENT_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{post-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(POST))
			.andExpect(content().string("{\"text\":\"This is a test comment\"}"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().addCommentToPost("g-4253322-S-89528249","This is a test comment");
	}
	
	@Test
	public void deleteComment() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_POST_DELETE_COMMENT_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{comment-id\\}", "g-4253322-S-89528249")))
			.andExpect(method(DELETE))
			.andExpect(content().string(""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().deleteOrFlagCommentAsInappropriate("g-4253322-S-89528249");
	}
	
	@Test
	public void deleteGroupSuggestion() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_SUGGESTION_DELETE_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{id\\}", "46964")))
			.andExpect(method(DELETE))
			.andExpect(content().string(""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().deleteGroupSuggestion(46964);
	}
	
	@Test
	public void joinGroup() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_JOIN_LEAVE_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "46964")))
			.andExpect(method(PUT))
			.andExpect(content().string("{\"membership-state\":{\"code\":\"member\"}}"))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().joinGroup(46964);
	}
	
	@Test
	public void leaveGroup() {
		mockServer.expect(requestTo((GroupTemplate.GROUP_JOIN_LEAVE_URL + "?oauth2_access_token=ACCESS_TOKEN").replaceFirst("\\{group-id\\}", "46964")))
			.andExpect(method(DELETE))
			.andExpect(content().string(""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
	
		linkedIn.groupOperations().leaveGroup(46964);
	}
	
}
