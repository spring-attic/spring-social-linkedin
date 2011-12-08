package org.springframework.social.linkedin.api;

import java.util.List;

/**
 * Operations related to sending messages and sending connect invitations to other
 * users on LinkedIn
 * 
 * @author Robert Drysdale
 *
 */
public interface CommunicationOperations {
	/**
	 * Send a textual message to a list of recipientIds
	 * Requires id from LinkedInProfile object
	 * 
	 * @param subject The subject of message
	 * @param body The body or text of message (does not support html)
	 * @param recipientIds List of ids
	 */
	void sendMessage(String subject, String body, List<String> recipientIds);
	
	/**
	 * Send a textual message to recipientId(s)
	 * Requires id from LinkedInProfile object
	 * 
	 * @param subject The subject of message
	 * @param body The body or text of message (does not support html)
	 * @param recipientIds One of more ids
	 */
	void sendMessage(String subject, String body, String... recipientIds);
	
	/**
	 * Send a connect invitation message to recipientId
	 * Requires id from LinkedInProfile object
	 * 
	 * @param subject The subject of message
	 * @param body The body or text of message (does not support html)
	 * @param recipientId Id of recipient
	 * @param apiStandardProfileRequest Part of LinkedInProfile returned when perfroming a search
	 */
	void connectTo(String subject, String body, String recipientId, ApiStandardProfileRequest apiStandardProfileRequest);
	
	/**
	 * Send a connect invitation message to and email (for users not on LinkedIn)
	 * 
	 * @param subject The subject of message
	 * @param body The body or text of message (does not support html)
	 * @param email Email address of recipient
	 * @param firstName First Name of recipient
	 * @param lastName Last Name of recipient
	 */
	void connectTo(String subject, String body, String email, String firstName, String lastName);
}
