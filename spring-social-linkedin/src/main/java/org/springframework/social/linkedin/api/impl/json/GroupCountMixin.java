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
import org.springframework.social.linkedin.api.Post.PostCategory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupCountMixin {
	
	@JsonCreator
	public GroupCountMixin(
			@JsonProperty("category") @JsonDeserialize(using=PostCategoryDeserializer.class) PostCategory category, 
			@JsonProperty("count") Integer count) {}
	
	public static final class PostCategoryDeserializer extends JsonDeserializer<PostCategory>  {
		public PostCategory deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return PostCategory.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}
}
