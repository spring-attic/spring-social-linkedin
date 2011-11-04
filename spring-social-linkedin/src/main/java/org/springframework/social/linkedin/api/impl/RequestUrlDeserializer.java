package org.springframework.social.linkedin.api.impl;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class RequestUrlDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode tree = jp.readValueAsTree();
		return tree.get("url").getValueAsText();
	}
}
