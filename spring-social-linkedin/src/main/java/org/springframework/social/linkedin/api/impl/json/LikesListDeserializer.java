/*
 * Copyright 2012 the original author or authors.
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
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.LinkedInProfile;

class LikesListDeserializer extends JsonDeserializer<List<LinkedInProfile>> {

	@Override
	public List<LinkedInProfile> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDeserializationConfig(ctxt.getConfig());
		jp.setCodec(mapper);
		if(jp.hasCurrentToken()) {
			JsonNode dataNode = jp.readValueAsTree().get("values");
			List<LinkedInProfile> likes = new ArrayList<LinkedInProfile>();
			// Have to iterate through list due to person sub object.
			for (JsonNode like : dataNode) {
				LinkedInProfile profile = mapper.readValue(like.get("person"), new TypeReference<LinkedInProfile>() {});
				likes.add(profile);
			}
			return likes;
		}
		
		return null;
	}

}