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
import org.springframework.social.linkedin.api.Group;
import org.springframework.social.linkedin.api.Group.MembershipState;
import org.springframework.social.linkedin.api.GroupSettings.EmailDigestFrequency;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupSettingsMixin {
	
	@JsonCreator
	public GroupSettingsMixin(@JsonProperty("allowMessagesFromMembers") Boolean allowMessagesFromMembers,
			@JsonProperty("emailAnnouncementsFromManagers") Boolean emailAnnouncementsFromManagers,
			@JsonProperty("emailDigestFrequency") @JsonDeserialize(using=EmailDigestFrequencyDeserializer.class) EmailDigestFrequency emailDigestFrequency,
			@JsonProperty("emailForEveryNewPost") Boolean emailForEveryNewPost,
			@JsonProperty("group") Group group,
			@JsonProperty("membershipState") @JsonDeserialize(using=MembershipStateDeserializer.class) MembershipState membershipState,
			@JsonProperty("showGroupLogoInProfile") Boolean showGroupLogoInProfile) {}
	
	
	public static final class EmailDigestFrequencyDeserializer extends JsonDeserializer<EmailDigestFrequency>  {
		public EmailDigestFrequency deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return EmailDigestFrequency.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}
	
	public static final class MembershipStateDeserializer extends JsonDeserializer<MembershipState>  {
		public MembershipState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return MembershipState.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}
}
