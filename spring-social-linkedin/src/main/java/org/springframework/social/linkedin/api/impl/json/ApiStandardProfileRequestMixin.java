package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonProperty;

public class ApiStandardProfileRequestMixin{
	
	public ApiStandardProfileRequestMixin(@JsonProperty("name") String name, @JsonProperty("value") String value) {}
}
