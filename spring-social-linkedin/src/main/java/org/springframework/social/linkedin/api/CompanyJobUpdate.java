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
package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Model class representing a Company Job Update
 * 
 * @author Robert Drysdale
 */
public class CompanyJobUpdate extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String action;
	
	private Job job;
	
	public CompanyJobUpdate(String action, Job job) {
		this.action = action;
		this.job = job;
	}

	public String getAction() {
		return action;
	}

	public Job getJob() {
		return job;
	}
}
