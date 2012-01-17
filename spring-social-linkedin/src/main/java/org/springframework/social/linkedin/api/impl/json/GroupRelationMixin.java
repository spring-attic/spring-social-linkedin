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

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRelationMixin {
	
	@JsonCreator
	public GroupRelationMixin(@JsonProperty("availableActions") @JsonDeserialize(using=AvailableActionDeserializer.class) List<GroupAvailableAction> availableActions, 
			@JsonProperty("membershipState") @JsonDeserialize(using=MembershipStateDeserializer.class) MembershipState membershipState) {}
	
	public static final class AvailableActionDeserializer extends JsonDeserializer<List<GroupAvailableAction>>  {
		public List<GroupAvailableAction> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			List<GroupAvailableAction> actions = new ArrayList<GroupAvailableAction>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						String s = d.path("code").getTextValue();
						if (s != null) {
							actions.add(GroupAvailableAction.valueOf(s.replace('-', '_').toUpperCase()));
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
