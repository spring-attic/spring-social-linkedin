/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class DeserializationUtils {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.registerModule(new LinkedInModule());
	}

	public static <T> T deserializeFromDataNode(JsonParser jp, DeserializationContext ctxt, String propertyName, TypeReference<T> typeReference) throws IOException, JsonProcessingException {
		if (jp.hasCurrentToken() && jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
			JsonNode dataNode = jp.readValueAs(JsonNode.class);
			if (dataNode.has(propertyName)) {
				return OBJECT_MAPPER.reader(typeReference).<T>readValue(dataNode.get(propertyName));
			}
			return null;
		}
		throw ctxt.mappingException("Expected JSON object");
	}

}
