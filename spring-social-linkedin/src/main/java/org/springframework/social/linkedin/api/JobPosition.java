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
import java.util.List;

/**
 * Details of position of a Job
 * 
 * @author Robert Drysdale
 */
public class JobPosition extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String title;
	
	private final Location location;
	
	private CodeAndName experienceLevel;
	
	private List<CodeAndName> industries;
	
	private List<CodeAndName> jobFunctions;
	
	private CodeAndName jobType;
	
	public JobPosition(String title, Location location) {
		this.title = title;
		this.location = location;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Location getLocation() {
		return location;
	}

	public CodeAndName getExperienceLevel() {
		return experienceLevel;
	}

	public List<CodeAndName> getIndustries() {
		return industries;
	}

	public List<CodeAndName> getJobFunctions() {
		return jobFunctions;
	}
	
	public CodeAndName getJobType() {
		return jobType;
	}

}
