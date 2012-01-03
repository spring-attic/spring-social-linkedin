package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyContactInfoMixin {
	CompanyContactInfoMixin(@JsonProperty(value="fax") String fax, 
			@JsonProperty(value="phone1") String phone1) {}
}
