package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.linkedin.api.Company.CompanyAddress;
import org.springframework.social.linkedin.api.Company.CompanyContactInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyLocationMixin {
	CompanyLocationMixin(@JsonProperty(value="address") CompanyAddress address, 
			@JsonProperty(value="contactInfo") CompanyContactInfo contactInfo) {}
}
