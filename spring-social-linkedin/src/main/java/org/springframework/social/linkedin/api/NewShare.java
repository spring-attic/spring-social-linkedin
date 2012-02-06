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

/**
 * Model object to represent a Share
 * 
 * @author Robert Drysdale
 */
public class NewShare implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String comment;
	
	private NewShareContent content;
	
	private NewShareVisibility visibility;
	
	public NewShare() {}
	
	public NewShare(String comment, NewShareContent content, NewShareVisibility visibility) {
		this.comment = comment;
		this.content = content;
		this.visibility = visibility;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public NewShareContent getContent() {
		return content;
	}
	
	public void setContent(NewShareContent content) {
		this.content = content;
	}
	
	public NewShareVisibility getVisibility() {
		return visibility;
	}
	
	public void setVisibility(NewShareVisibility visibility) {
		this.visibility = visibility;
	}
	
	public static class NewShareContent implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String title;
		private String submittedUrl;
		private String submittedImageUrl;
		private String description;
		
		public NewShareContent() {}
		
		public NewShareContent(String title, String submittedUrl) {
			this.title = title;
			this.submittedUrl = submittedUrl;
		}
		
		public NewShareContent(String title, String submittedUrl, String description) {
			this(title,submittedUrl);
			this.description = description;
		}
		
		public NewShareContent(String title, String submittedUrl, String submittedImageUrl, String description) {
			this(title,submittedUrl,description);
			this.submittedImageUrl = submittedImageUrl;
		}
		
		public String getTitle() {
			return title;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getSubmittedUrl() {
			return submittedUrl;
		}
		
		public void setSubmittedUrl(String submittedUrl) {
			this.submittedUrl = submittedUrl;
		}
		
		public String getSubmittedImageUrl(String submittedImageUrl) {
			return this.submittedImageUrl;
		}
		
		public void setSubmittedImageUrl(String submittedImageUrl) {
			this.submittedImageUrl = submittedImageUrl;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	public static class NewShareVisibility implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private NewShareVisibilityCode code;
		
		public NewShareVisibility() {}
		
		public NewShareVisibility(NewShareVisibilityCode code) {
			this.code = code;
		}
		
		public NewShareVisibilityCode getCode() {
			return code;
		}
		
		public void setCode(NewShareVisibilityCode code) {
			this.code = code;
		}
	}
	
	public static enum NewShareVisibilityCode {
		ANYONE,
		CONNECTIONS_ONLY;
		
		public String toString() {
			return this.name().toLowerCase().replace('_', '-');
		}
	}

}
