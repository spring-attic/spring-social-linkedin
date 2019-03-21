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
package org.springframework.social.linkedin.api.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.ConnectionAuthorization;

public class CommunicationTemplateTest extends AbstractLinkedInApiTest {
	@Test
	public void sendMessage() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL+"?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(jsonPath("body", is("This is a test")))
			.andExpect(jsonPath("subject", is("Test message")))
			.andExpect(jsonPath("recipients.values", hasSize(1)))
			.andExpect(jsonPath("recipients.values[0].person._path", is("/people/~")))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));

		linkedIn.communicationOperations().sendMessage("Test message", "This is a test", "~");
	}
	
	@Test
	public void sendInvitation() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL+"?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(jsonPath("body", is("I'd like to add you to my professional network on LinkedIn")))
			.andExpect(jsonPath("subject", is("I'd like to add you to my professional network on LinkedIn")))
			.andExpect(jsonPath("recipients.values", hasSize(1)))
			.andExpect(jsonPath("recipients.values[0].person._path", is("/people/UB2kruYvvv")))
			.andExpect(jsonPath("item-content.invitation-request.authorization.name", is("NAME_SEARCH")))
			.andExpect(jsonPath("item-content.invitation-request.authorization.value", is("aaaa")))
			.andExpect(jsonPath("item-content.invitation-request.connect-type", is("friend")))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));

		linkedIn.communicationOperations().connectTo("I'd like to add you to my professional network on LinkedIn",
				"I'd like to add you to my professional network on LinkedIn", "UB2kruYvvv", new ConnectionAuthorization("blah", "NAME_SEARCH:aaaa"));
	}
	
	@Test
	public void sendEmailInvitation() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL+"?oauth2_access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(jsonPath("body", is("I'd like to add you to my professional network on LinkedIn")))
			.andExpect(jsonPath("subject", is("I'd like to add you to my professional network on LinkedIn")))
			.andExpect(jsonPath("recipients.values", hasSize(1)))
			.andExpect(jsonPath("recipients.values[0].person._path", is("/people/email=rob@test.com")))
			.andExpect(jsonPath("recipients.values[0].person.first-name", is("Robert")))
			.andExpect(jsonPath("recipients.values[0].person.last-name", is("Smith")))
			.andExpect(jsonPath("item-content.invitation-request.connect-type", is("friend")))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));

		linkedIn.communicationOperations().connectTo("I'd like to add you to my professional network on LinkedIn",
				"I'd like to add you to my professional network on LinkedIn", "rob@test.com", "Robert", "Smith");
	}
	
}
