/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.UpdateAction;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentCompany;
import org.springframework.social.linkedin.api.UpdateContentConnection;
import org.springframework.social.linkedin.api.UpdateContentFollow;
import org.springframework.social.linkedin.api.UpdateContentGroup;
import org.springframework.social.linkedin.api.UpdateContentPersonActivity;
import org.springframework.social.linkedin.api.UpdateContentRecommendation;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateContentStatus;
import org.springframework.social.linkedin.api.UpdateContentViral;
import org.springframework.social.linkedin.api.UpdateType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class LinkedInNetworkUpdateListDeserializer extends JsonDeserializer<LinkedInNetworkUpdate> {

	@Override
	public LinkedInNetworkUpdate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new LinkedInModule());
		jp.setCodec(mapper);
		
		JsonNode dataNode = jp.readValueAs(JsonNode.class);
		if(dataNode != null) {
			LinkedInNetworkUpdate linkedInNetworkUpdate = (LinkedInNetworkUpdate) mapper.reader(new TypeReference<LinkedInNetworkUpdate>() {}).readValue(dataNode);
			
			UpdateContent updatedContent = null;
			UpdateType type = linkedInNetworkUpdate.getUpdateType();
			JsonNode updatedNode = dataNode.get("updateContent");
			JsonNode person = updatedNode.get("person");
			if (type == UpdateType.MSFC) {
				// Totally different.  Looks like a bad API to be honest.
				person = updatedNode.get("companyPersonUpdate").get("person");
			}
			
			switch (type) {
				case CONN:
					updatedContent = mapper.reader(new TypeReference<UpdateContentConnection>() {}).readValue(person);
					break;
				case STAT:
					updatedContent = mapper.reader(new TypeReference<UpdateContentStatus>() {}).readValue(person);
					break;
				case JGRP:
					updatedContent = mapper.reader(new TypeReference<UpdateContentGroup>() {}).readValue(person);
					break;
				case PREC:
				case SVPR:
					updatedContent = mapper.reader(new TypeReference<UpdateContentRecommendation>() {}).readValue(person);
					break;
				case APPM:
					updatedContent = mapper.reader(new TypeReference<UpdateContentPersonActivity>() {}).readValue(person);
					break;
				case MSFC:
					updatedContent = mapper.reader(new TypeReference<UpdateContentFollow>() {}).readValue(person);
					break;
				case VIRL:
					updatedContent = mapper.reader(new TypeReference<UpdateContentViral>() {}).readValue(person);
					break;
				case SHAR:
					updatedContent = mapper.reader(new TypeReference<UpdateContentShare>() {}).readValue(person);
					break;
				case CMPY:
					updatedContent = mapper.reader(new TypeReference<UpdateContentCompany>() {}).readValue(updatedNode);
					break;
				default:
					try {
						updatedContent = mapper.reader(new TypeReference<UpdateContent>() {}).readValue(person);
					}
					catch (Exception e) {
						throw new RuntimeException(e);
					}
			}
			
			// Need to use reflection to set private updateContent field
			try {
				Field f = LinkedInNetworkUpdate.class.getDeclaredField("updateContent");
				f.setAccessible(true);
				f.set(linkedInNetworkUpdate, updatedContent);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			if (type == UpdateType.MSFC) {
				// Set the action via reflection as it's private
				String action = updatedNode.get("companyPersonUpdate").get("action").get("code").asText();
				try {
					Field f = UpdateContentFollow.class.getDeclaredField("action");
					f.setAccessible(true);
					f.set(updatedContent, action);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				// Set following via reflection as it's private
				Company company = mapper.reader(new TypeReference<Company>() {}).readValue(updatedNode.get("company"));
				try {
					Field f = UpdateContentFollow.class.getDeclaredField("following");
					f.setAccessible(true);
					f.set(updatedContent, company);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else if (type == UpdateType.VIRL) {
				JsonNode originalUpdate =  updatedNode.path("updateAction").path("originalUpdate");
				UpdateAction updateAction = mapper.reader(new TypeReference<UpdateAction>() {}).readValue(originalUpdate);
				String code = updatedNode.path("updateAction").path("action").path("code").textValue();
				
				// Set private immutable field action on updateAction
				try {
					Field f = UpdateAction.class.getDeclaredField("action");
					f.setAccessible(true);
					f.set(updateAction, code);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				// Set private immutable field  updateAction on updatedContent
				try {
					Field f = UpdateContentViral.class.getDeclaredField("updateAction");
					f.setAccessible(true);
					f.set(updatedContent, updateAction);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			return linkedInNetworkUpdate;
		}
		
		return null;
	}

}
