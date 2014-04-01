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
import java.util.Date;

/**
 * Model class representing a share.
 * 
 * @author Robert Drysdale
 */
public class Share extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String comment;

	private ShareContent content;
	
	private String id;
	
	private ShareSource source;
	
	private Date timestamp;
	
	private String visibility;
	
	public Share(String comment, ShareContent content, String id, ShareSource source, Date timestamp, String visibility) {
		this.comment = comment;
		this.content = content;
		this.id = id;
		this.source = source;
		this.timestamp = timestamp;
		this.visibility = visibility;
	}
	
	public Share() {
		super();
	}

	public String getComment() {
		return comment;
	}
	
	public ShareContent getContent() {
		return content;
	}
	
	public String getId() {
		return id;
	}
	
	public ShareSource getSource() {
		return source;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getVisibility() {
		return visibility;
	}
	
	public static class ShareContent extends LinkedInObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String description;
		
		private String eyebrowUrl;
		
		private String shortenedUrl;
		
		private String submittedImageUrl;
		
		private String submittedUrl;
		
		private String thumbnailUrl;
		
		private String title;
		
		public ShareContent(String description, String eyebrowUrl, String shortenedUrl, String submittedImageUrl, String submittedUrl, String thumbnailUrl, String title) {
			this.description = description;
			this.eyebrowUrl = eyebrowUrl;
			this.shortenedUrl = shortenedUrl;
			this.submittedImageUrl = submittedImageUrl;
			this.submittedUrl = submittedUrl;
			this.thumbnailUrl = thumbnailUrl;
			this.title = title;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getEyebrowUrl() {
			return eyebrowUrl;
		}
		
		public String getShortenedUrl() {
			return shortenedUrl;
		}
		
		public String getSubmittedImageUrl() {
			return submittedImageUrl;
		}
		
		public String getSubmittedUrl() {
			return submittedUrl;
		}
		
		public String getThumbnailUrl() {
			return thumbnailUrl;
		}
		
		public String getTitle() {
			return title;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setEyebrowUrl(String eyebrowUrl) {
			this.eyebrowUrl = eyebrowUrl;
		}

		public void setShortenedUrl(String shortenedUrl) {
			this.shortenedUrl = shortenedUrl;
		}

		public void setSubmittedImageUrl(String submittedImageUrl) {
			this.submittedImageUrl = submittedImageUrl;
		}

		public void setSubmittedUrl(String submittedUrl) {
			this.submittedUrl = submittedUrl;
		}

		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
	}
	
	
	public static class ShareSource extends LinkedInObject implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String application;
		
		private String serviceProvider;
		
		private String serviceProviderAccountHandle;
		
		private String serviceProviderAccountId;
		
		private String serviceProviderShareId;
		
		public ShareSource(String application, String serviceProvider, String serviceProviderAccountHandle, String serviceProviderAccountId, String serviceProviderShareId) {
			this.application = application;
			this.serviceProvider = serviceProvider;
			this.serviceProviderAccountHandle = serviceProviderAccountHandle;
			this.serviceProviderAccountId = serviceProviderAccountId;
			this.serviceProviderShareId = serviceProviderShareId;
		}
		
		public ShareSource() {
			super();
		}

		public String getApplication() {
			return application;
		}
		
		public String getServiceProvider() {
			return serviceProvider;
		}
		
		public String getServiceProviderAccountHandle() {
			return serviceProviderAccountHandle;
		}
		
		public String getServiceProviderAccountId() {
			return serviceProviderAccountId;
		}
		
		public String getServiceProviderShareId() {
			return serviceProviderShareId;
		}

		public void setApplication(String application) {
			this.application = application;
		}

		public void setServiceProvider(String serviceProvider) {
			this.serviceProvider = serviceProvider;
		}

		public void setServiceProviderAccountHandle(String serviceProviderAccountHandle) {
			this.serviceProviderAccountHandle = serviceProviderAccountHandle;
		}

		public void setServiceProviderAccountId(String serviceProviderAccountId) {
			this.serviceProviderAccountId = serviceProviderAccountId;
		}

		public void setServiceProviderShareId(String serviceProviderShareId) {
			this.serviceProviderShareId = serviceProviderShareId;
		}

		
	}

}
