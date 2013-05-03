/*
 * Copyright 2013 the original author or authors.
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
package org.springframework.social.linkedin.api;

import java.util.List;

/**
 * Operations related to User Connections on LinkedIn
 * 
 * @author Robert Drysdale
 */
public interface ConnectionOperations {
	
	/**
	 * Retrieves up to 500 of the 1st-degree connections from the current user's network.
	 * @return the user's connections
	 */
	List<LinkedInProfile> getConnections();
	
	/**
	 * Retrieves the 1st-degree connections from the current user's network.
	 * @param start The starting location in the result set. Used with count for pagination.
	 * @param count The number of connections to return. The maximum value is 500. Used with start for pagination.
	 * @return the user's connections
	 */
	List<LinkedInProfile> getConnections(int start, int count);
	
	/**
	 * Retrieve Network Statistics for User.
	 * Contains Count of First Degree and Second Degree Connections.
	 * Requires "rw_nus" permission.
	 * @return Network Statistics
	 */
	NetworkStatistics getNetworkStatistics();

}
