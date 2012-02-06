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

/**
 * Job Bookmark
 * 
 * @author Robert Drysdale
 *
 */
public class JobBookmark implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final boolean isApplied;
	private final boolean isSaved;
	private final Job job;
	private final Date savedTimestamp;
	
	public JobBookmark(boolean isApplied, boolean isSaved, Job job, Date savedTimestamp) {
		this.isApplied = isApplied;
		this.isSaved = isSaved;
		this.job = job;
		this.savedTimestamp = savedTimestamp;
	}

	public boolean isApplied() {
		return isApplied;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public Job getJob() {
		return job;
	}

	public Date getSavedTimestamp() {
		return savedTimestamp;
	}
}
