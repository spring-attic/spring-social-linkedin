package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.Post.PostRelation;
import org.springframework.social.linkedin.api.Post.PostType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostMixin {
	
	@JsonCreator
	public PostMixin(
			@JsonProperty("creator") LinkedInProfile creator, 
			@JsonProperty("id") String id,
			@JsonProperty("title") String title,
			@JsonProperty("type") @JsonDeserialize(using=PostTypeDeserializer.class) PostType type) {}
	
	@JsonProperty Date creationTimestamp;
	@JsonProperty PostRelation relationToViewer;
	@JsonProperty String summary;
	@JsonProperty @JsonDeserialize(using=LikesListDeserializer.class) List<LinkedInProfile> likes;
	
	public static final class PostTypeDeserializer extends JsonDeserializer<PostType>  {
		public PostType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return PostType.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}
	
	public static final class LikesListDeserializer extends JsonDeserializer<List<LinkedInProfile>>  {
		public List<LinkedInProfile> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			List<LinkedInProfile> likes = new ArrayList<LinkedInProfile>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						LinkedInProfile p = mapper.readValue(d.path("person"), new TypeReference<LinkedInProfile>() {});
						likes.add(p);
					}
				}
			}
			return likes;
		}
	}
}
