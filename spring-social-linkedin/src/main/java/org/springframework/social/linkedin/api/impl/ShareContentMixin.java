package org.springframework.social.linkedin.api.impl;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareContentMixin {
	
	@JsonCreator
	public ShareContentMixin(@JsonProperty("author") String description, 
			@JsonProperty("eyebrowUrl") String eyebrowUrl, 
			@JsonProperty("shortenedUrl") String shortenedUrl,
			@JsonProperty("submittedImageUrl") String submittedImageUrl,
			@JsonProperty("submittedUrl") String submittedUrl, 
			@JsonProperty("thumbnailUrl") String thumbnailUrl, 
			@JsonProperty("title") String title) {}
}
