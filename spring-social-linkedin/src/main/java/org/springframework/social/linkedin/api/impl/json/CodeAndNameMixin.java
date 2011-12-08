package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeAndNameMixin {
	@JsonCreator
	CodeAndNameMixin(@JsonProperty(value="code") String code, 
			@JsonProperty(value="name") String name) {}
}
