package org.springframework.social.linkedin.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.Group;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupSuggestionsMixin {
	
	@JsonCreator
	public GroupSuggestionsMixin(@JsonProperty("_count") int count, 
			@JsonProperty("_start") int start, 
			@JsonProperty("_total") int total) {}
	
	@JsonProperty("values") List<Group> suggestions;
}
