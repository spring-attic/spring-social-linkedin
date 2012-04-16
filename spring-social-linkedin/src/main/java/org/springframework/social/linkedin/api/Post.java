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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model class representing a Post.
 * 
 * @author Robert Drysdale
 */
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;	

	private final LinkedInProfile creator;
	
	private final String id;
	
	private final String title;
	
	private final PostType type;
	
	private Date creationTimestamp;
	
	private PostRelation relationToViewer;
	
	private String summary;
	
	private List<LinkedInProfile> likes;
	
	private Attachment attachment;
	
	public Post(LinkedInProfile creator, String id, String title, PostType type) {
		this.creator = creator;
		this.id = id;
		this.title = title;
		this.type = type;
	}
	
	public LinkedInProfile getCreator() {
		return creator;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public PostType getType() {
		return type;
	}
	
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	
	public PostRelation getRelationToViewer() {
		return relationToViewer;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public List<LinkedInProfile> getLikes() {
		return likes;
	}
	
	public Attachment getAttachment() {
		return attachment;
	}
	
	public static class PostRelation implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final Boolean isFollowing;
		
		private final Boolean isLiked;
		
		private final List<PostAvailableAction> availableActions;
		
		public PostRelation(List<PostAvailableAction> availableActions, Boolean isFollowing, Boolean isLiked) {
			this.availableActions = availableActions;
			this.isFollowing = isFollowing;
			this.isLiked = isLiked;
		}
		
		public Boolean isFollowing() {
			return isFollowing;
		}
		
		public Boolean isLiked() {
			return isLiked;
		}
		
		public List<PostAvailableAction> getAvailableActions() {
			return availableActions;
		}
	}
	
	public static class Attachment implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final String contentDomain;

		private final String contentUrl;
		
		private final String imageUrl;
		
		private final String summary;
		
		private final String title;
		
		public Attachment(String contentDomain, String contentUrl, String imageUrl, String summary, String title) {
			this.contentDomain = contentDomain;
			this.contentUrl = contentUrl;
			this.imageUrl = imageUrl;
			this.summary = summary;
			this.title = title;
		}
		
		public String getContentDomain() {
			return contentDomain;
		}
		
		public String getContentUrl() {
			return contentUrl;
		}
		
		public String getImageUrl() {
			return imageUrl;
		}
		
		public String getSummary() {
			return summary;
		}
		
		public String getTitle() {
			return title;
		}
	}
	
	public static enum PostCategory {
		DISCUSSION,
		JOB
	}
	
	public static enum PostType {
		STANDARD,
		NEWS
	}
	
	public static enum PostAvailableAction {
		ADD_COMMENT,
		FLAG_AS_INAPPROPRIATE,
		CATEGORIZE_AS_JOB,
		CATEGORIZE_AS_PROMOTION,
		DELETE,
		FOLLOW,
		LIKE,
		REPLY_PRIVATELY,
		UNFOLLOW
	}

}
