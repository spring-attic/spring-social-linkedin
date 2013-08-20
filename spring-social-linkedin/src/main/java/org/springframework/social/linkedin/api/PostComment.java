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
 * Comment on an object such as a post or update
 * 
 * @author Robert Drysdale
 */
public class PostComment extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Date creationTimestamp;
	
	private final LinkedInProfile creator;
	
	private final String id;
	
	private final String text;
	
	public PostComment(Date creationTimestamp, LinkedInProfile creator, String id, String text) {
		this.creationTimestamp = creationTimestamp;
		this.creator = creator;
		this.id = id;
		this.text = text;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public LinkedInProfile getCreator() {
		return creator;
	}
	
	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

}
