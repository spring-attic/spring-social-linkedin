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

import org.springframework.social.linkedin.api.Post.PostCategory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GroupCountMixin {
	
	@JsonCreator
	GroupCountMixin(
		@JsonProperty("category") @JsonDeserialize(using=PostCategoryDeserializer.class) PostCategory category, 
		@JsonProperty("count") Integer count) {}
	
	private static final class PostCategoryDeserializer extends JsonDeserializer<PostCategory>  {
		public PostCategory deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if(jp.hasCurrentToken() && jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
				JsonNode node = jp.readValueAs(JsonNode.class);
				if (node.has("code")) {
					return PostCategory.valueOf(node.get("code").textValue().replace('-', '_').toUpperCase());
				}
			}
			throw ctxt.mappingException("Expected JSON object");
		}
	}

}
