/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.social.linkedin.api.impl;

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
import org.springframework.social.linkedin.api.UpdateType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedInNetworkUpdateMixin {

	@JsonCreator
	public LinkedInNetworkUpdateMixin(
			@JsonProperty("timestamp") Date timestamp, 
			@JsonProperty("updateKey") String updateKey, 
			@JsonProperty("updateType") @JsonDeserialize(using = UpdateTypeDeserializer.class) UpdateType updateType) {}
	
	@JsonProperty("isCommentable") 
	boolean commentable;
	
	@JsonProperty("isLikable") 
	boolean likeable;
	
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
	
	private static class UpdateTypeDeserializer extends JsonDeserializer<UpdateType> {
		@Override
		public UpdateType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return UpdateType.valueOf(jp.getText().toUpperCase());
		}
	}
	
	private static class LikesListDeserializer extends JsonDeserializer<List<LinkedInProfile>> {
		@SuppressWarnings("unchecked")
		@Override
		public List<LinkedInProfile> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				return (List<LinkedInProfile>) mapper.readValue(dataNode, new TypeReference<List<LinkedInProfile>>() {});
			}
			
			return null;
		}
	}
	
	private static class UpdatedFieldsListDeserializer extends JsonDeserializer<List<String>> {
		@Override
		public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				List<String> values = new ArrayList<String>();
				for (JsonNode value : dataNode) {
					values.add(value.get("name").getTextValue());
				}
				
				return values;
			}
			
			return null;
		}
	}
}
