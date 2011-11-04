package org.springframework.social.linkedin.api.impl;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.UpdateContent;

public class UpdateContentDeserializer extends JsonDeserializer<UpdateContent> {
	@Override
	public UpdateContent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDeserializationConfig(ctxt.getConfig());
		jp.setCodec(mapper);
		if(jp.hasCurrentToken()) {
			JsonNode dataNode = jp.readValueAsTree();
			if(dataNode != null) {
				return (UpdateContent) mapper.readValue(dataNode, new TypeReference<UpdateContent>() {});
			}
		}
		
		return null;
	}
}
