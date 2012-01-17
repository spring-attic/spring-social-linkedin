package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.social.linkedin.api.Group.GroupAvailableAction;
import org.springframework.social.linkedin.api.Group.MembershipState;
import org.springframework.social.linkedin.api.Post.PostAvailableAction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRelationMixin {
	
	@JsonCreator
	public PostRelationMixin(@JsonProperty("availableActions") @JsonDeserialize(using=AvailableActionDeserializer.class) List<GroupAvailableAction> availableActions, 
			@JsonProperty("isFollowing") Boolean isFollowing, 
			@JsonProperty("isLiked") Boolean isLiked) {}
	
	public static final class AvailableActionDeserializer extends JsonDeserializer<List<PostAvailableAction>>  {
		public List<PostAvailableAction> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			List<PostAvailableAction> actions = new ArrayList<PostAvailableAction>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						String s = d.path("code").getTextValue();
						if (s != null) {
							actions.add(PostAvailableAction.valueOf(s.replace('-', '_').toUpperCase()));
						}
					}
				}
			}
			return actions;
		}
	}
	
	public static final class MembershipStateDeserializer extends JsonDeserializer<MembershipState>  {
		public MembershipState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return MembershipState.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}
}
