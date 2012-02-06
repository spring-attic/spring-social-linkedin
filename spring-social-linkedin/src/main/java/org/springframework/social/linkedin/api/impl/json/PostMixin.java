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
import org.springframework.social.linkedin.api.Post.PostRelation;
import org.springframework.social.linkedin.api.Post.PostType;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class PostMixin {
	
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
			JsonNode node = jp.readValueAsTree();
			return PostType.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}
	
	private static final class LikesListDeserializer extends JsonDeserializer<List<LinkedInProfile>>  {
		public List<LinkedInProfile> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			List<LinkedInProfile> likes = new ArrayList<LinkedInProfile>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						LinkedInProfile p = mapper.readValue(d.path("person"), new TypeReference<LinkedInProfile>() {});
						likes.add(p);
					}
				}
			}
			return likes;
		}
	}

}
