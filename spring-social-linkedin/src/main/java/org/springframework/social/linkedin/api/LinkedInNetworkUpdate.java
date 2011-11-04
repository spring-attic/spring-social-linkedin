/*
 * Copyright 2010 the original author or authors.
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
 * Model class containing a user's LinkedIn Network update information.
 * @author Robert Drysdale
 */
public class LinkedInNetworkUpdate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Date timestamp;

	private final String updateKey;
	
	private final UpdateType updateType;
	
	private boolean commentable;
	
	private boolean likable;
	
	private boolean liked;
	
	private int numLikes;
	
	private List<LinkedInProfile> likes;
	
	private UpdateContent updateContent;
	
	private List<String> updatedFields;
	
	public LinkedInNetworkUpdate(Date timestamp, String updateKey, UpdateType updateType) {
		this.timestamp = timestamp;
		this.updateKey = updateKey;
		this.updateType = updateType;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getUpdateKey() {
		return updateKey;
	}
	
	public UpdateType getUpdateType() {
		return updateType;
	}
	
	public UpdateContent getUpdateContent() {
		return updateContent;
	}
	
	public boolean isCommentable() {
		return commentable;
	}
	
	public boolean isLikable() {
		return likable;
	}
	
	public boolean isLiked() {
		return liked;
	}
	
	public int getNumLikes() {
		return numLikes;
	}
	
	public List<LinkedInProfile> getLikes() {
		return likes;
	}
	
	public List<String> getUpdatedFields() {
		return updatedFields;
	}
	
}
