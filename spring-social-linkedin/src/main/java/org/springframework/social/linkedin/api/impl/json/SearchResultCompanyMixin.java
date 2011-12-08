package org.springframework.social.linkedin.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.Company;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultCompanyMixin {
	
	@JsonCreator
	public SearchResultCompanyMixin(@JsonProperty("_count") int count, 
			@JsonProperty("_start") int start, 
			@JsonProperty("_total") int total) {}
	
	@JsonProperty("values") List<Company> companies;
}
