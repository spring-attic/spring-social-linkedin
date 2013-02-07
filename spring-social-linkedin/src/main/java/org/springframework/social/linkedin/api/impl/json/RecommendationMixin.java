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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.Recommendation.RecommendationType;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class RecommendationMixin {

	@JsonCreator
	RecommendationMixin(
		@JsonProperty("id") String id, 
		@JsonProperty("recommendationSnippet") String recommendationSnippet, 
		@JsonProperty("recommendationText") String recommendationText, 
		@JsonProperty("recommendationType") @JsonDeserialize(using=RecommendationTypeDeserializer.class) RecommendationType recommendationType,
		@JsonProperty("recommender") LinkedInProfile recommender,
		@JsonProperty("recommendee") LinkedInProfile recommendee) {}
	
	private static class RecommendationTypeDeserializer extends JsonDeserializer<RecommendationType> {
		@Override
		public RecommendationType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = jp.readValueAsTree();
			return RecommendationType.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
		}
	}

}
