package org.springframework.social.linkedin.api.impl;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import org.junit.Test;
import org.springframework.social.linkedin.api.ApiStandardProfileRequest;

public class CommunicationTemplateTest extends AbstractLinkedInApiTest {
	@Test
	public void sendMessage() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL))
		.andExpect(method(POST))
		.andExpect(body("{\"body\":\"This is a test\",\"recipients\":{\"values\":[{\"person\":{\"_path\":\"/people/~\"}}]},\"subject\":\"Test message\"}"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		
		linkedIn.communicationOperations().sendMessage("Test message", "This is a test", "~");
	}
	
	@Test
	public void sendInvitation() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL))
		.andExpect(method(POST))
		.andExpect(body("{\"body\":\"I'd like to add you to my professional network on LinkedIn\",\"recipients\":{\"values\":[{\"person\":{\"_path\":\"/people/UB2kruYvvv\"}}]},\"subject\":\"I'd like to add you to my professional network on LinkedIn\",\"itemContent\":{\"invitationRequest\":{\"connectType\":\"friend\",\"authorization\":{\"name\":\"NAME_SEARCH\",\"value\":\"aaaa\"}}}}"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		
		
		linkedIn.communicationOperations().connectTo("I'd like to add you to my professional network on LinkedIn",
				"I'd like to add you to my professional network on LinkedIn", "UB2kruYvvv", new ApiStandardProfileRequest("blah", "NAME_SEARCH:aaaa"));

	}
	
	@Test
	public void sendEmailInvitation() {
		mockServer.expect(requestTo(CommunicationTemplate.MESSAGING_URL))
		.andExpect(method(POST))
		.andExpect(body("{\"body\":\"I'd like to add you to my professional network on LinkedIn\",\"recipients\":{\"values\":[{\"person\":{\"_path\":\"/people/email=rob@test.com\",\"firstName\":\"Robert\",\"lastName\":\"Smith\"}}]},\"subject\":\"I'd like to add you to my professional network on LinkedIn\",\"itemContent\":{\"invitationRequest\":{\"connectType\":\"friend\"}}}"))
		.andExpect(headerContains("Authorization", "OAuth oauth_version=\"1.0\", oauth_nonce=\""))
		.andExpect(headerContains("Authorization", "oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"API_KEY\", oauth_token=\"ACCESS_TOKEN\", oauth_timestamp=\""))
		.andExpect(headerContains("Authorization", "oauth_signature=\""))
		.andRespond(withResponse("", responseHeaders));
		
		
		linkedIn.communicationOperations().connectTo("I'd like to add you to my professional network on LinkedIn",
				"I'd like to add you to my professional network on LinkedIn", "rob@test.com", "Robert", "Smith");

	}
}
