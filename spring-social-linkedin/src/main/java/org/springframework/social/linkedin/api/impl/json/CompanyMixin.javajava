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

import org.springframework.social.linkedin.api.CodeAndName;
import org.springframework.social.linkedin.api.Company.CompanyLocation;

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
abstract class CompanyMixin extends LinkedInObjectMixin {

	@JsonCreator
	CompanyMixin(
		@JsonProperty("id") int id, 
		@JsonProperty("name") String name) {}
	

	@JsonProperty("industry") 
	String industry;
	
	@JsonProperty("size") 
	String size;
	
	@JsonProperty("type") 
	String type;
	
	@JsonProperty 
	String blogRssUrl;
	
	@JsonProperty 
	CodeAndName companyType;
	
	@JsonProperty 
	String description;
	
	@JsonProperty 
	@JsonDeserialize(using=StringListDeserializer.class) 
	List<String> emailDomains;
	
	@JsonProperty 
	CodeAndName employeeCountRange;
	
	@JsonProperty 
	int foundedYear;
	
	@JsonProperty 
	@JsonDeserialize(using=CompanyLocationListDeserializer.class) 
	List<CompanyLocation> locations;
	
	@JsonProperty 
	String logoUrl;
	
	@JsonProperty 
	int numFollowers;
	
	@JsonProperty 
	@JsonDeserialize(using=StringListDeserializer.class) 
	List<String> specialties;
	
	@JsonProperty 
	String squareLogoUrl;
	
	@JsonProperty 
	CodeAndName status;
	
	@JsonProperty 
	CodeAndName stockExchange;
	
	@JsonProperty 
	String ticker;
	
	@JsonProperty 
	String twitterId;
	
	@JsonProperty 
	String universalName;
	
	@JsonProperty 
	String websiteUrl;
	
	private static final class CompanyLocationListDeserializer extends JsonDeserializer<List<CompanyLocation>>  {
		public List<CompanyLocation> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return DeserializationUtils.deserializeFromDataNode(jp, ctxt, "values", new TypeReference<List<CompanyLocation>>() {});
		}
	}
	
}
