package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareSourceMixin {
	
	@JsonCreator
	public ShareSourceMixin(@JsonProperty("application") @JsonDeserialize(using=NameDeserializer.class) String application, 
			@JsonProperty("serviceProvider")  @JsonDeserialize(using=NameDeserializer.class) String serviceProvider,
			@JsonProperty("serviceProviderAccountHandle") String serviceProviderAccountHandle, 
			@JsonProperty("serviceProviderAccountId") String serviceProviderAccountId,
			@JsonProperty("serviceProviderShareId") String serviceProviderShareId) {}
	
	private static class NameDeserializer extends JsonDeserializer<String> {
		@Override
		public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return node.get("name").getTextValue();
		}
	}
}
