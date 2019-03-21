/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
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
import java.util.Date;
import java.util.List;

import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.UpdateType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class LinkedInNetworkUpdateMixin extends LinkedInObjectMixin {

	@JsonCreator
	LinkedInNetworkUpdateMixin(
		@JsonProperty("timestamp") Date timestamp, 
		@JsonProperty("updateKey") String updateKey, 
		@JsonProperty("updateType") @JsonDeserialize(using = UpdateTypeDeserializer.class) UpdateType updateType) {}
	
	@JsonProperty("isCommentable") 
	boolean commentable;
	
	@JsonProperty("isLikable") 
	boolean likable;
	
	@JsonProperty("isLiked") 
	boolean liked;
	
	@JsonProperty("numLikes") 
	int numLikes;
	
	@JsonProperty("likes")
	@JsonDeserialize(using = LikesListDeserializer.class)
	List<LinkedInProfile> likes;
	
	
	@JsonProperty("updatedFields")
	@JsonDeserialize(using = UpdatedFieldsListDeserializer.class)
	List<String> updatedFields;
	
	private static class UpdatedFieldsListDeserializer extends JsonDeserializer<List<String>> {
		@Override
		public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new LinkedInModule());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
				List<String> values = new ArrayList<String>();
				for (JsonNode value : dataNode) {
					values.add(value.get("name").textValue());
				}
				
				return values;
			}
			
			return null;
		}
	}

}
