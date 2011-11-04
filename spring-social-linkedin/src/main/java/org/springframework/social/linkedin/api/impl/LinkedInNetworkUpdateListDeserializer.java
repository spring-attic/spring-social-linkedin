package org.springframework.social.linkedin.api.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentConnection;
import org.springframework.social.linkedin.api.UpdateContentFollow;
import org.springframework.social.linkedin.api.UpdateContentGroup;
import org.springframework.social.linkedin.api.UpdateContentPersonActivity;
import org.springframework.social.linkedin.api.UpdateContentRecommendation;
import org.springframework.social.linkedin.api.UpdateContentStatus;
import org.springframework.social.linkedin.api.UpdateType;

public class LinkedInNetworkUpdateListDeserializer extends JsonDeserializer<List<LinkedInNetworkUpdate>> {
	@Override
	public List<LinkedInNetworkUpdate> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDeserializationConfig(ctxt.getConfig());
		jp.setCodec(mapper);
		List<LinkedInNetworkUpdate> linkedInNetworkUpdates = new ArrayList<LinkedInNetworkUpdate>();
		
		if(jp.hasCurrentToken()) {
			// Iterate through list of Network Updates
			for (JsonNode dataNode : jp.readValueAsTree()) {
				if(dataNode != null) {
					LinkedInNetworkUpdate linkedInNetworkUpdate = (LinkedInNetworkUpdate) mapper.readValue(dataNode, new TypeReference<LinkedInNetworkUpdate>() {});
					
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
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContentConnection>() {});
							break;
						case STAT:
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContentStatus>() {});
							break;
						case JGRP:
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContentGroup>() {});
							break;
						case PREC:
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContentRecommendation>() {});
							break;
						case APPM:
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContentPersonActivity>() {});
							break;
						case MSFC:
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContentFollow>() {});
							break;
						default:
							updatedContent = mapper.readValue(person, new TypeReference<UpdateContent>() {});
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
						String action = updatedNode.get("companyPersonUpdate").get("action").get("code").getValueAsText();
						try {
							Field f = UpdateContentFollow.class.getDeclaredField("action");
							f.setAccessible(true);
							f.set(updatedContent, action);
						}
						catch (Exception e) {
							throw new RuntimeException(e);
						}
						
						// Set following via reflection as it's private
						Company company = mapper.readValue(updatedNode.get("company"), new TypeReference<Company>() {});
						try {
							Field f = UpdateContentFollow.class.getDeclaredField("following");
							f.setAccessible(true);
							f.set(updatedContent, company);
						}
						catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					
					linkedInNetworkUpdates.add(linkedInNetworkUpdate);
				}
			}
		}

		return linkedInNetworkUpdates;
	}
}
