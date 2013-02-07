/*
 * Copyright 2013 the original author or authors.
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
import org.springframework.social.linkedin.api.Comment;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentCompany;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateType;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class UpdateActionMixin {

	@JsonCreator
	UpdateActionMixin(
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
	
	@JsonProperty("updateComments")
	@JsonDeserialize(using = CommentsListDeserializer.class )
	List<Comment> updateComments;
	
	@JsonProperty("updateContent")
	@JsonDeserialize(using = UpdateContentDeserializer.class)
	UpdateContent updateContent;
	
	private static class CommentsListDeserializer extends JsonDeserializer<List<Comment>>  {
		public List<Comment> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<Comment>>() {} );
				}
			}
			return null;
		}
	}
	
	private static class UpdateContentDeserializer extends JsonDeserializer<UpdateContent> {
		@Override
		public UpdateContent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			
			JsonNode content = jp.readValueAsTree();
			JsonNode person = content.get("person");
			JsonNode company = content.get("company");
			// person for a SHAR update
			if (person != null) {
				return mapper.readValue(person, new TypeReference<UpdateContentShare>() {});
			}
			// company and companyStatusUpdate for CMPY update
			else if (company != null) {
				return mapper.readValue(content, new TypeReference<UpdateContentCompany>() {});
			}
			return null;
		}
		
	}
	
}
