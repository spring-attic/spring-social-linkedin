package org.springframework.social.linkedin.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.LinkedInProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultMixin {
	
	@JsonCreator
	public SearchResultMixin(@JsonProperty("_count") int count, 
			@JsonProperty("_start") int start, 
			@JsonProperty("_total") int total, 
			@JsonProperty("values") List<LinkedInProfile> people) {}
}
