package org.springframework.social.linkedin.api;

import java.util.List;

public interface ConnectionOperations {
	/**
	 * Retrieves the 1st-degree connections from the current user's network.
	 * 
	 * @return the user's connections
	 */
	List<LinkedInProfile> getConnections();
}
