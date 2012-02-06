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
package org.springframework.social.linkedin.api.impl;

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.linkedin.api.ApiStandardProfileRequest;
import org.springframework.social.linkedin.api.CommunicationOperations;
import org.springframework.web.client.RestOperations;

/**
 * Class that implements communication API for sending messages and invitations
 *  
 * @author Robert Drysdale
 */
class CommunicationTemplate implements CommunicationOperations {

	private final RestOperations restOperations;
	
	public CommunicationTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public void sendMessage(String subject, String body, List<String> recipientIds) {
		Map<String, Object> mailboxItem = new HashMap<String,Object>();
		
		mailboxItem.put("recipients", new Recipients(recipientIds));
		mailboxItem.put("subject", subject);
		mailboxItem.put("body", body);
		
		restOperations.postForLocation(MESSAGING_URL, mailboxItem);
	}
	
	public void sendMessage(String subject, String body, String... recipientIds) {
		sendMessage(subject, body, Arrays.asList(recipientIds));
	}
	
	public void connectTo(String subject, String body, String recipientId, ApiStandardProfileRequest apiStandardProfileRequest) {
		Map<String, Object> mailboxItem = new HashMap<String,Object>();
		
		mailboxItem.put("recipients", new Recipients(Arrays.asList(recipientId)));
		mailboxItem.put("subject", subject);
		mailboxItem.put("body", body);
		String[] nameValue = apiStandardProfileRequest.getValue().split(":");
		mailboxItem.put("itemContent", new ItemContent(nameValue[0], nameValue[1]));
		
		restOperations.postForLocation(MESSAGING_URL, mailboxItem);
	}
	
	public void connectTo(String subject, String body, String email, String firstName, String lastName) {
		Map<String, Object> mailboxItem = new HashMap<String,Object>();
		
		mailboxItem.put("recipients", new Recipients(email, firstName, lastName));
		mailboxItem.put("subject", subject);
		mailboxItem.put("body", body);
		mailboxItem.put("itemContent", new ItemContent());
		
		restOperations.postForLocation(MESSAGING_URL, mailboxItem);
	}
	
	
	
	public static final String MESSAGING_URL = BASE_URL + "~/mailbox";
	
	public static final String PEOPLE = "/people/";
	
	private static final class ItemContent {
		private final InvitationRequest invitationRequest;
		
		private ItemContent(String name, String value) {
			invitationRequest = new InvitationRequest(name, value);
		}
		
		private ItemContent() {
			invitationRequest = new InvitationRequest();
		}

		@SuppressWarnings("unused")
		public InvitationRequest getInvitationRequest() {
			return invitationRequest;
		}
	}
	
	private static final class InvitationRequest {
		private final String connectType = "friend"; 
		private final Authorization authorization;
		private InvitationRequest(String name, String value) {
			authorization = new Authorization(name, value);
		}
		
		private InvitationRequest() {
			authorization = null;
		}
		

		@SuppressWarnings("unused")
		public String getConnectType() {
			return connectType;
		}
		

		@SuppressWarnings("unused")
		public Authorization getAuthorization() {
			return authorization;
		}
	}
	
	private static final class Authorization {
		private final String name;
		private final String value;
		
		private Authorization(String name, String value) {
			this.name = name;
			this.value = value;
		}
		

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}
		

		@SuppressWarnings("unused")
		public String getValue() {
			return value;
		}
	}
	
	private static final class Recipients {
		private final List<Recipient> values;
		
		private Recipients(List<String> recipientIds) {
			values = new ArrayList<Recipient>();
			for (String r : recipientIds) {
				values.add(new Recipient(PEOPLE + r));
			}
			
		}
		
		private Recipients(String email, String firstName, String lastName) {
			values = Collections.singletonList(new Recipient(PEOPLE +"email=" + email, firstName, lastName));
		}
		
		private Recipients(String recipientId) {
			values = Collections.singletonList(new Recipient(PEOPLE + recipientId));
		}
		
		@SuppressWarnings("unused")
		public List<Recipient> getValues() {
			return values;
		}
	}
	
	private static final class Recipient {
		private final Person person;
		
		private Recipient(String person) {
			this.person = (new Person(person));
		}
		
		private Recipient(String person, String firstName, String lastName) {
			this.person = (new Person(person, firstName, lastName));
		}

		@SuppressWarnings("unused")
		public final Person getPerson() {
			return person;
		}
	}
	
	private static final class Person {
		private final String _path;
		
		private final String firstName;
		
		private final String lastName;
		
		private Person(String _path) {
			this._path = _path;
			this.firstName = null;
			this.lastName = null;
		}
		
		private Person(String _path, String firstName, String lastName) {
			this._path= _path;
			this.firstName = firstName;
			this.lastName = lastName;
		}
		

		@SuppressWarnings("unused")
		public String get_path() {
			return _path;
		}
		

		@SuppressWarnings("unused")
		public String getFirstName() {
			return firstName;
		}
		

		@SuppressWarnings("unused")
		public String getLastName() {
			return lastName;
		}
	}

}
