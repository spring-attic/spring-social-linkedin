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
 * Model class representing a comment on an object such as a post or update
 * 
 * @author Robert Drysdale
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String comment;
	
	private final String id;
	
	private final LinkedInProfile person;
	
	private final int sequenceNumber;
	
	private final Date timestamp;
	
	public Comment(String comment, String id, LinkedInProfile person, int sequenceNumber, Date timestamp) {
		this.comment = comment;
		this.id = id;
		this.person = person;
		this.sequenceNumber = sequenceNumber;
		this.timestamp = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public String getId() {
		return id;
	}

	public LinkedInProfile getPerson() {
		return person;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
}
