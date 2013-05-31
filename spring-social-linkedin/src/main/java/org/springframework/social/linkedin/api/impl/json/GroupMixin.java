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
import java.util.List;
import java.util.Map;

import org.springframework.social.linkedin.api.Group.GroupCategory;
import org.springframework.social.linkedin.api.Group.GroupCount;
import org.springframework.social.linkedin.api.Group.GroupPosts;
import org.springframework.social.linkedin.api.Group.GroupRelation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GroupMixin {

	@JsonCreator
	GroupMixin(
		@JsonProperty("id") Integer id, 
		@JsonProperty("name") String name) {}
	
	@JsonProperty 
	Boolean allowMemberInvites;
	
	@JsonProperty 
	@JsonDeserialize(using=GroupCategoryDeserializer.class) 
	GroupCategory category;
	
	@JsonProperty 
	@JsonDeserialize(using=GroupCountDeserializer.class) 
	List<GroupCount> countsByCategory;
	
	@JsonProperty 
	String description;
	
	@JsonProperty 
	Boolean isOpenToNonMembers;
	
	@JsonProperty 
	String largeLogoUrl;
	
	@JsonProperty 
	String locale;
	
	@JsonProperty 
	GroupPosts posts;
	
	@JsonProperty 
	GroupRelation relationToViewer;
	
	@JsonProperty 
	String shortDescription;
	
	@JsonProperty 
	String siteGroupUrl;
	
	@JsonProperty 
	String smallLogoUrl;
	
	@JsonProperty 
	String websiteUrl;
	
	private static final class GroupCategoryDeserializer extends JsonDeserializer<GroupCategory>  {
		public GroupCategory deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			Map<?,?> nodeMap = jp.readValueAs(Map.class);
			return GroupCategory.valueOf(nodeMap.get("code").toString().replace('-', '_').toUpperCase());
		}
	}
	
	private static final class GroupCountDeserializer extends JsonDeserializer<List<GroupCount>> {
		public List<GroupCount> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<GroupCount>>() {});
		}
	}
	
}
