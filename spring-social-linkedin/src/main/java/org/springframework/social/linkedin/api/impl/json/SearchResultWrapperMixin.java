package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.SearchResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultWrapperMixin {
	
	@JsonCreator
	public SearchResultWrapperMixin(@JsonProperty("people") SearchResult result) {}
}
