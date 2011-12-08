package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyAddressMixin {
	CompanyAddressMixin(@JsonProperty(value="city") String city, 
			@JsonProperty(value="postalCode") String postalcode,
			@JsonProperty(value="street1") String street1) {}
}
