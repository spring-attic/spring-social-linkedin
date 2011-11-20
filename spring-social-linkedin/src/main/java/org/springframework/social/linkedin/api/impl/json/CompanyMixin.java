/*
 * Copyright 2011 the original author or authors.
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
import org.springframework.social.linkedin.api.CodeAndName;
import org.springframework.social.linkedin.api.Company.CompanyLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyMixin {

	@JsonCreator
	public CompanyMixin(
			@JsonProperty("id") String id, 
			@JsonProperty("name") String name) {}
	

	@JsonProperty("industry") String industry;
	@JsonProperty("size") String size;
	@JsonProperty("type") String type;
	@JsonProperty String blogRssUrl;
	@JsonProperty CodeAndName companyType;
	@JsonProperty String description;
	@JsonProperty @JsonDeserialize(using=StringListDeserializer.class) List<String> emailDomains;
	@JsonProperty CodeAndName employeeCountRange;
	@JsonProperty int foundedYear;
	@JsonProperty @JsonDeserialize(using=CompanyLocationListDeserializer.class) List<CompanyLocation> locations;
	@JsonProperty String logoUrl;
	@JsonProperty int numFollowers;
	@JsonProperty @JsonDeserialize(using=StringListDeserializer.class) List<String> specialties;
	@JsonProperty String squareLogoUrl;
	@JsonProperty CodeAndName status;
	@JsonProperty CodeAndName stockExchange;
	@JsonProperty String ticker;
	@JsonProperty String twitterId;
	@JsonProperty String universalName;
	@JsonProperty String websiteUrl;
	
	public static final class StringListDeserializer extends JsonDeserializer<List<String>>  {
		public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<String>>() {} );
				}
			}
			return null;
		}
	}
	
	public static final class CompanyLocationListDeserializer extends JsonDeserializer<List<CompanyLocation>>  {
		public List<CompanyLocation> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<CompanyLocation>>() {} );
				}
			}
			return null;
		}
	}
	
}
