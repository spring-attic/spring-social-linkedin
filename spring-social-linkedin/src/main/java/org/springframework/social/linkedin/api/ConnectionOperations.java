package org.springframework.social.linkedin.api;

import java.util.List;

/**
 * Operations related to User Connections on LinkedIn
 * 
 * @author Robert Drysdale
 *
 */
public interface ConnectionOperations {
	/**
	 * Retrieves the 1st-degree connections from the current user's network.
	 * 
	 * @return the user's connections
	 */
	List<LinkedInProfile> getConnections();
	
	/**
	 * Retrieve Network Statistics for User
	 * Contains Count of First Degree and Second Degree Connections
	 * 
	 * @return Network Statistics
	 */
	NetworkStatistics getNetworkStatistics();
}
