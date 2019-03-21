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
 * Recommendation
 * 
 * @author Robert Drysdale
 */
public class Recommendation extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String id;

	private final String recommendationSnippet;
	
	private final String recommendationText;
	
	private final RecommendationType recommendationType;
	
	private final LinkedInProfile recommender;
	
	private final LinkedInProfile recommendee;
	
	public Recommendation(String id, String recommendationSnippet, String recommendationText, RecommendationType recommendationType, LinkedInProfile recommender, LinkedInProfile recommendee) {
		this.id = id;
		this.recommendationSnippet = recommendationSnippet;
		this.recommendationText = recommendationText;
		this.recommendationType = recommendationType;
		this.recommender = recommender;
		this.recommendee = recommendee;
	}
	
	public String getId() {
		return id;
	}
	
	public String getRecommendationSnippet() {
		return recommendationSnippet;
	}
	
	public String getRecommendationText() {
		return recommendationText;
	}
	
	public RecommendationType getRecommendationType() {
		return recommendationType;
	}
	
	public LinkedInProfile getRecommender() {
		return recommender;
	}
	
	public LinkedInProfile getRecommendee() {
		return recommendee;
	}
	
	public static enum RecommendationType {
		BUSINESS_PARTNER,
		COLLEAGUE,
		EDUCATION,
		SERVICE_PROVIDER
	}

}
