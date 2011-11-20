package org.springframework.social.linkedin.api.impl.json;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.LinkedInProfile;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRecommendationMixin {
	@JsonCreator
	public ProductRecommendationMixin(
			@JsonProperty(value="id") int id, 
			@JsonProperty(value="productId") int productId,
			@JsonProperty(value="recommender") LinkedInProfile recommender, 
			@JsonProperty(value="text") String text, 
			@JsonProperty(value="timestamp") Date timestamp) {}
}
