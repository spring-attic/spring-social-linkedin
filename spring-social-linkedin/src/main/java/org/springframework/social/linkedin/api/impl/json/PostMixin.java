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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.Post.PostRelation;
import org.springframework.social.linkedin.api.Post.PostType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class PostMixin extends LinkedInObjectMixin {
	
	@JsonCreator
	PostMixin(
		@JsonProperty("creator") LinkedInProfile creator, 
		@JsonProperty("id") String id,
		@JsonProperty("title") String title,
		@JsonProperty("type") @JsonDeserialize(using=PostTypeDeserializer.class) PostType type) {}
	
	@JsonProperty 
	Date creationTimestamp;
	
	@JsonProperty 
	PostRelation relationToViewer;
	
	@JsonProperty 
	String summary;
	
	@JsonProperty 
	@JsonDeserialize(using=LikesListDeserializer.class) 
	List<LinkedInProfile> likes;
	
	private static final class PostTypeDeserializer extends JsonDeserializer<PostType>  {
		public PostType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAs(JsonNode.class);
			return PostType.valueOf(node.get("code").textValue().replace('-', '_').toUpperCase());
		}
	}
	
	private static final class LikesListDeserializer extends JsonDeserializer<List<LinkedInProfile>>  {
		public List<LinkedInProfile> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new LinkedInModule());
			jp.setCodec(mapper);
			List<LinkedInProfile> likes = new ArrayList<LinkedInProfile>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						LinkedInProfile p = mapper.reader(new TypeReference<LinkedInProfile>() {}).readValue(d.path("person"));
						likes.add(p);
					}
				}
			}
			return likes;
		}
	}

}
