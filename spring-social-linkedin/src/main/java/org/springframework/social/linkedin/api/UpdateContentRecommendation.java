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

import java.util.List;

/**
 * A recommendation update
 * 
 * @author Robert Drysdale
 */
public class UpdateContentRecommendation extends UpdateContent {

	private static final long serialVersionUID = 1L;
	
	private List<Recommendation> recommendationsGiven;
	
	private List<Recommendation> recommendationsReceived;
	
	public UpdateContentRecommendation(String id, String firstName, String lastName, String headline, String industry, String publicProfileUrl, UrlResource siteStandardProfileRequest, String profilePictureUrl) {
		super(id, firstName, lastName, headline, industry, publicProfileUrl, siteStandardProfileRequest, profilePictureUrl);
	}
	
	public List<Recommendation> getRecommendationsGiven() {
		return recommendationsGiven;
	}
	
	public List<Recommendation> getRecommendationsReceived() {
		return recommendationsReceived;
	}

}
