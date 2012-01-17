package org.springframework.social.linkedin.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.Post;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentMixin {
	
	@JsonCreator
	public AttachmentMixin(@JsonProperty("contentDomain") String contentDomain, 
			@JsonProperty("contentUrl") String contentUrl, 
			@JsonProperty("imageUrl") String imageUrl, 
			@JsonProperty("summary") String summary, 
			@JsonProperty("title") String title) {}
	
	@JsonProperty("values")  List<Post> posts;
}
