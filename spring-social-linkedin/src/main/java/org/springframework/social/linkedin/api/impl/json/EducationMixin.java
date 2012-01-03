/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.LinkedInDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationMixin {

	@JsonCreator
	public EducationMixin(
			@JsonProperty("activities") String activities, 
			@JsonProperty("degree")  String degree,
			@JsonProperty("fieldOfStudy")  String fieldOfStudy,
			@JsonProperty("id")  String id,
			@JsonProperty("notes")  String notes,
			@JsonProperty("schoolName")  String schoolName,
			@JsonProperty("startDate")  LinkedInDate startDate,
			@JsonProperty("endDate")  LinkedInDate endDte) {}
	
}
