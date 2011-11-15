package org.springframework.social.linkedin.api.impl;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.linkedin.api.Share.ShareContent;
import org.springframework.social.linkedin.api.Share.ShareSource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareMixin {
	
	@JsonCreator
	public ShareMixin(
			@JsonProperty("comment") String comment, 
			@JsonProperty("content") ShareContent content,
			@JsonProperty("id") String id, 
			@JsonProperty("source") ShareSource source, 
			@JsonProperty("timestamp") Date timestamp, 
			@JsonProperty("visibility") @JsonDeserialize(using=CodeDeserializer.class) String visibility) {}
}
