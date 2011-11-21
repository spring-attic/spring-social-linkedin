package org.springframework.social.linkedin.api;

import java.util.List;

public interface CommunicationOperations {
	void sendMessage(String subject, String body, List<String> recipientIds);
	
	void sendMessage(String subject, String body, String... recipientIds);
	
	void connectTo(String subject, String body, String recipientId, ApiStandardProfileRequest apiStandardProfileRequest);
	
	void connectTo(String subject, String body, String email, String firstName, String lastName);
}
