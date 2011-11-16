package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class StandardListDeserializer<T> extends JsonDeserializer<List<T>> {
	@SuppressWarnings("unchecked")
	@Override
	public List<T> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDeserializationConfig(ctxt.getConfig());
		jp.setCodec(mapper);
		if(jp.hasCurrentToken()) {
			JsonNode dataNode = jp.readValueAsTree().get("values");
			if (dataNode != null) {
				return (List<T>) mapper.readValue(dataNode, new TypeReference<List<T>>() {});
			}
		}
		
		return null;
	}
}
