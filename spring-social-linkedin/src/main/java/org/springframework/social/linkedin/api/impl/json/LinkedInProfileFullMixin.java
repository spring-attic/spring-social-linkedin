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
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.ApiStandardProfileRequest;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.Education;
import org.springframework.social.linkedin.api.ImAccount;
import org.springframework.social.linkedin.api.LinkedInDate;
import org.springframework.social.linkedin.api.Location;
import org.springframework.social.linkedin.api.PhoneNumber;
import org.springframework.social.linkedin.api.Position;
import org.springframework.social.linkedin.api.Recommendation;
import org.springframework.social.linkedin.api.Relation;
import org.springframework.social.linkedin.api.TwitterAccount;
import org.springframework.social.linkedin.api.UrlResource;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class LinkedInProfileFullMixin {

	@JsonCreator
	LinkedInProfileFullMixin(
		@JsonProperty("id") String id, 
		@JsonProperty("firstName") String firstName, 
		@JsonProperty("lastName") String lastName, 
		@JsonProperty("headline") String headline, 
		@JsonProperty("industry") String industry, 
		@JsonProperty("publicProfileUrl") String publicProfileUrl, 
		@JsonProperty("siteStandardProfileRequest") UrlResource siteStandardProfileRequest, 
		@JsonProperty("pictureUrl") String profilePictureUrl) {}
	
	@JsonProperty @JsonDeserialize(using=PositionListDeserializer.class)
	List<Position> positions;
	
	@JsonProperty @JsonDeserialize(using=PositionListDeserializer.class)
	List<Position> threeCurrentPositions;
	
	@JsonProperty @JsonDeserialize(using=PositionListDeserializer.class)
	List<Position> threePastPositions;
	
	@JsonProperty @JsonDeserialize(using=RecommendationsListDeserializer.class)
	List<Recommendation> recommendationsReceived;
	
	@JsonProperty @JsonDeserialize(using=ImAccountListDeserializer.class)
	List<ImAccount> imAccounts;
	
	@JsonProperty @JsonDeserialize(using=TwitterAccountListDeserializer.class)
	List<TwitterAccount> twitterAccounts;
	
	@JsonProperty @JsonDeserialize(using=UrlResourceListDeserializer.class)
	List<UrlResource> memberUrlResources;
	
	@JsonProperty @JsonDeserialize(using=PhoneNumberListDeserializer.class)
	List<PhoneNumber> phoneNumbers;
	
	@JsonProperty @JsonDeserialize(using=SkillListDeserializer.class)
	List<String> skills;
	
	@JsonProperty @JsonDeserialize(using=EducationListDeserializer.class)
	List<Education> educations;
	
	@JsonProperty("summary")
	String summary;
	
	@JsonProperty
	String proposalComments;
	
	@JsonProperty
	String specialties;
	
	@JsonProperty
	int numConnections;
	
	@JsonProperty
	boolean numConnectionsCapped;
	
	@JsonProperty
	int numRecommenders;
	
	@JsonProperty
	String mainAddress;
	
	@JsonProperty
	String associations;
	
	@JsonProperty
	Location location;
	
	@JsonProperty
	String interests;
	
	@JsonProperty
	String honors;
	
	@JsonProperty
	int distance;
	
	@JsonProperty
	LinkedInDate dateOfBirth;
	
	@JsonProperty
	CurrentShare currentShare;
	
	@JsonProperty
	Relation relationToViewer;
	
	@JsonProperty("apiStandardProfileRequest")
	@JsonDeserialize(using=ApiStandardProfileRequestDeserializer.class) 
	ApiStandardProfileRequest apiStandardProfileRequest;
	
	private static final class PositionListDeserializer extends JsonDeserializer<List<Position>>  {
		public List<Position> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<Position>>() {} );
				}
			}
			return null;
		}
	}
	
	private static final class ImAccountListDeserializer extends JsonDeserializer<List<ImAccount>>  {
		public List<ImAccount> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<ImAccount>>() {} );
				}
			}
			return null;
		}
	}
	
	private static final class TwitterAccountListDeserializer extends JsonDeserializer<List<TwitterAccount>>  {
		public List<TwitterAccount> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<TwitterAccount>>() {} );
				}
			}
			return null;
		}
	}
	
	private static final class UrlResourceListDeserializer extends JsonDeserializer<List<UrlResource>>  {
		public List<UrlResource> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<UrlResource>>() {} );
				}
			}
			return null;
		}
	}
	
	private static final class PhoneNumberListDeserializer extends JsonDeserializer<List<PhoneNumber>>  {
		public List<PhoneNumber> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<PhoneNumber>>() {} );
				}
			}
			return null;
		}
	}
	
	private static final class EducationListDeserializer extends JsonDeserializer<List<Education>>  {
		public List<Education> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<Education>>() {} );
				}
			}
			return null;
		}
	}
	
	private static final class SkillListDeserializer extends JsonDeserializer<List<String>> {
		@Override
		public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			List<String> skills = new ArrayList<String>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						String s = d.path("skill").path("name").getTextValue();
						if (s != null) {
							skills.add(s);
						}
					}
				}
			}
			
			return skills;
		}
	}
	
}
