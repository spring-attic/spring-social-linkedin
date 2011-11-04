package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Recommendation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final String recommendationSnippet;
	private final RecommendationType recommendationType;
	private final LinkedInProfile recommender;
	
	public Recommendation(String id, String recommendationSnippet, 
			RecommendationType recommendationType, LinkedInProfile recommender) {
		this.id = id;
		this.recommendationSnippet = recommendationSnippet;
		this.recommendationType = recommendationType;
		this.recommender = recommender;
	}
	
	public String getId() {
		return id;
	}
	
	public String getRecommendationSnippet() {
		return recommendationSnippet;
	}
	
	public RecommendationType getRecommendationType() {
		return recommendationType;
	}
	
	public LinkedInProfile getRecommender() {
		return recommender;
	}
	
	public static enum RecommendationType {
		BUSINESS_PARTNER,
		COLLEAGUE,
		EDUCATION,
		SERVICE_PROVIDER
		}
}
