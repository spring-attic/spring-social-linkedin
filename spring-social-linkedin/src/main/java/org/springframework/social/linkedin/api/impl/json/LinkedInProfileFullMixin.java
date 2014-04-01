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
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.linkedin.api.ConnectionAuthorization;
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class LinkedInProfileFullMixin extends LinkedInObjectMixin {

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
	@JsonDeserialize(using=ConnectionAuthorizationDeserializer.class) 
	ConnectionAuthorization connectionAuthorization;
	
	private static final class PositionListDeserializer extends JsonDeserializer<List<Position>>  {
		public List<Position> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<Position>>() {});
		}
	}
	
	private static final class ImAccountListDeserializer extends JsonDeserializer<List<ImAccount>>  {
		public List<ImAccount> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<ImAccount>>() {});
		}
	}
	
	private static final class TwitterAccountListDeserializer extends JsonDeserializer<List<TwitterAccount>>  {
		public List<TwitterAccount> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<TwitterAccount>>() {});
		}
	}
	
	private static final class UrlResourceListDeserializer extends JsonDeserializer<List<UrlResource>>  {
		public List<UrlResource> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<UrlResource>>() {});
		}
	}
	
	private static final class PhoneNumberListDeserializer extends JsonDeserializer<List<PhoneNumber>>  {
		public List<PhoneNumber> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<PhoneNumber>>() {});
		}
	}
	
	private static final class EducationListDeserializer extends JsonDeserializer<List<Education>>  {
		public List<Education> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<Education>>() {});
		}
	}
	
	private static final class SkillListDeserializer extends JsonDeserializer<List<String>> {
		@Override
		public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if (jp.hasCurrentToken() && jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
				JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
				List<String> skills = new ArrayList<String>();
				if (dataNode != null) {
					for (JsonNode d : dataNode) {
						String s = d.path("skill").path("name").textValue();
						if (s != null) {
							skills.add(s);
						}
					}
				}
				return skills;
			}
			throw ctxt.mappingException("Expected JSON object");
		}
	}
	@JsonProperty("pictureUrl") 
	String profilePictureUrl;
}
