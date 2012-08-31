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

import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.ConnectionAuthorization;

public class CommunicationTemplateTest extends AbstractLinkedInApiTest {
	@Test
	public void sendMessage() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL))
			.andExpect(method(POST))
			.andExpect(body("{\"body\":\"This is a test\",\"recipients\":{\"values\":[{\"person\":{\"_path\":\"/people/~\"}}]},\"subject\":\"Test message\"}"))
			.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
			.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
			.andExpect(headerContains("Authorization", "oauth_signature=\""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		linkedIn.communicationOperations().sendMessage("Test message", "This is a test", "~");
	}
	
	@Test
	public void sendInvitation() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL))
			.andExpect(method(POST))
			.andExpect(body("{\"body\":\"I'd like to add you to my professional network on LinkedIn\",\"recipients\":{\"values\":[{\"person\":{\"_path\":\"/people/UB2kruYvvv\"}}]},\"subject\":\"I'd like to add you to my professional network on LinkedIn\",\"item-content\":{\"invitation-request\":{\"authorization\":{\"name\":\"NAME_SEARCH\",\"value\":\"aaaa\"},\"connect-type\":\"friend\"}}}"))
			.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
			.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
			.andExpect(headerContains("Authorization", "oauth_signature=\""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		
		linkedIn.communicationOperations().connectTo("I'd like to add you to my professional network on LinkedIn",
				"I'd like to add you to my professional network on LinkedIn", "UB2kruYvvv", new ConnectionAuthorization("blah", "NAME_SEARCH:aaaa"));

	}
	
	@Test
	public void sendEmailInvitation() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL))
			.andExpect(method(POST))
			.andExpect(body("{\"body\":\"I'd like to add you to my professional network on LinkedIn\",\"recipients\":{\"values\":[{\"person\":{\"_path\":\"/people/email=rob@test.com\",\"first-name\":\"Robert\",\"last-name\":\"Smith\"}}]},\"subject\":\"I'd like to add you to my professional network on LinkedIn\",\"item-content\":{\"invitation-request\":{\"connect-type\":\"friend\"}}}"))
			.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
			.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
			.andExpect(headerContains("Authorization", "oauth_signature=\""))
			.andRespond(withSuccess("", MediaType.APPLICATION_JSON));
		
		
		linkedIn.communicationOperations().connectTo("I'd like to add you to my professional network on LinkedIn",
				"I'd like to add you to my professional network on LinkedIn", "rob@test.com", "Robert", "Smith");

	}
}
