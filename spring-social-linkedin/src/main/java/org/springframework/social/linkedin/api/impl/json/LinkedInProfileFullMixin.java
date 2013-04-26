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
import org.springframework.social.linkedin.api.ConnectionAuthorization;
import org.springframework.social.linkedin.api.Course;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.Education;
import org.springframework.social.linkedin.api.ImAccount;
import org.springframework.social.linkedin.api.LinkedInDate;
import org.springframework.social.linkedin.api.Location;
import org.springframework.social.linkedin.api.Patent;
import org.springframework.social.linkedin.api.PhoneNumber;
import org.springframework.social.linkedin.api.Position;
import org.springframework.social.linkedin.api.Publication;
import org.springframework.social.linkedin.api.Recommendation;
import org.springframework.social.linkedin.api.Relation;
import org.springframework.social.linkedin.api.TwitterAccount;
import org.springframework.social.linkedin.api.UrlResource;
import org.springframework.social.linkedin.api.Volunteer;

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
	
	@JsonProperty("emailAddress")
	String emailAddress;
	
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
	
	@JsonProperty @JsonDeserialize(using=LanguageListDeserializer.class)
	List<String> languages;
	
	@JsonProperty @JsonDeserialize(using=EducationListDeserializer.class)
	List<Education> educations;
	
	@JsonProperty @JsonDeserialize(using=CertificationListDeserializer.class)
	List<String> certifications;
	
	@JsonProperty @JsonDeserialize(using=CourseListDeserializer.class)
	List<Course> courses;
	
	@JsonProperty @JsonDeserialize(using=PatentListDeserializer.class)
	List<Patent> patents;
	
	@JsonProperty @JsonDeserialize(using=PublicationListDeserializer.class)
	List<Publication> publications;
	
	@JsonProperty @JsonDeserialize(using=VolunteerListDeserializer.class)
	List<Volunteer> volunteer;
	
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

	private static JsonNode getJsonNode(JsonNode node, String path) {
		if(path != null && path.length() > 0) {
			for(String pathElem : path.split("\\.")) {
				node = node.path(pathElem);
			}
		}
		return node;
	}

	private static class GenericLinkedInListDeserializer<T> extends JsonDeserializer<List<T>>  {
		protected final String listPath;
		protected final TypeReference<List<T>> typeReference;
		
		public GenericLinkedInListDeserializer(String listPath, TypeReference<List<T>> typeReference) {
			this.listPath = listPath;
			this.typeReference = typeReference;
		}

		@Override
		public List<T> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = getJsonNode(jp.readValueAsTree(), listPath);
				if (dataNode != null && !dataNode.isMissingNode()) {
					return mapper.readValue(dataNode, typeReference);
				}
			}
			return null;
		}
	}
	
	private static final class PositionListDeserializer extends GenericLinkedInListDeserializer<Position>  {
		public PositionListDeserializer() {
			super("values", new TypeReference<List<Position>>() {});
		}
	}
	
	private static final class ImAccountListDeserializer extends GenericLinkedInListDeserializer<ImAccount>  {
		public ImAccountListDeserializer() {
			super("values", new TypeReference<List<ImAccount>>() {});
		}
	}
	
	private static final class TwitterAccountListDeserializer extends GenericLinkedInListDeserializer<TwitterAccount>  {
		public TwitterAccountListDeserializer() {
			super("values", new TypeReference<List<TwitterAccount>>() {});
		}
	}
	
	private static final class UrlResourceListDeserializer extends GenericLinkedInListDeserializer<UrlResource>  {
		public UrlResourceListDeserializer() {
			super("values", new TypeReference<List<UrlResource>>() {});
		}
	}
	
	private static final class PhoneNumberListDeserializer extends GenericLinkedInListDeserializer<PhoneNumber>  {
		public PhoneNumberListDeserializer() {
			super("values", new TypeReference<List<PhoneNumber>>() {});
		}
	}
	
	private static final class EducationListDeserializer extends GenericLinkedInListDeserializer<Education>  {
		public EducationListDeserializer() {
			super("values", new TypeReference<List<Education>>() {});
		}
	}
	
	private static final class CourseListDeserializer extends GenericLinkedInListDeserializer<Course>  {
		public CourseListDeserializer() {
			super("values", new TypeReference<List<Course>>() {});
		}
	}
	
	private static final class PatentListDeserializer extends GenericLinkedInListDeserializer<Patent>  {
		public PatentListDeserializer() {
			super("values", new TypeReference<List<Patent>>() {});
		}
	}
	
	private static final class PublicationListDeserializer extends GenericLinkedInListDeserializer<Publication>  {
		public PublicationListDeserializer() {
			super("values", new TypeReference<List<Publication>>() {});
		}
	}
	
	private static final class VolunteerListDeserializer extends GenericLinkedInListDeserializer<Volunteer>  {
		public VolunteerListDeserializer() {
			super("volunteerExperiences.values", new TypeReference<List<Volunteer>>() {});
		}
	}
	
	private static class StringListDeserializer extends JsonDeserializer<List<String>> {
		protected final String listPath;
		protected final String listElementPath;

		public StringListDeserializer(String listPath, String listElementPath) {
			this.listPath = listPath;
			this.listElementPath = listElementPath;
		}

		@Override
		public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			List<String> strings = new ArrayList<String>();
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = getJsonNode(jp.readValueAsTree(), listPath);
				if (dataNode != null && !dataNode.isMissingNode()) {
					for (JsonNode d : dataNode) {
						String s = getJsonNode(d, listElementPath).getTextValue();
						if (s != null) {
							strings.add(s);
						}
					}
				}
			}
			
			return strings;
		}
	}

	private static final class SkillListDeserializer extends StringListDeserializer {
		public SkillListDeserializer() { super("values", "skill.name"); }
	}
	
	private static final class LanguageListDeserializer extends StringListDeserializer {
		public LanguageListDeserializer() { super("values", "language.name"); }
	}
	
	private static final class CertificationListDeserializer extends StringListDeserializer {
		public CertificationListDeserializer() { super("values", "name"); }
	}
	
}
