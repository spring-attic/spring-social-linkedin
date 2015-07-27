/*
 * Copyright 2014 the original author or authors.
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

import org.springframework.social.ApiBinding;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.web.client.RestOperations;

/**
 * Interface specifying a basic set of operations for interacting with LinkedIn. Implemented by {@link LinkedInTemplate}.
 * Many of the methods contained in this interface require OAuth authentication with LinkedIn. 
 * When a method's description speaks of the "current user", it is referring to the user for whom the access token has been issued.
 * 
 * @author Craig Walls
 * @author Robert Drysdale
 */
public interface LinkedIn extends ApiBinding {

	/**
	 * API for retrieving connections
	 * @return a {@link ConnectionOperations} for working with connections
	 */
	ConnectionOperations connectionOperations();
	
	/**
	 * API for retrieving and performing operations on network updates
	 * @return a {@link NetworkUpdateOperations} for working with network updates
	 */
	NetworkUpdateOperations networkUpdateOperations();
	
	/**
	 * API for retrieving and performing operations on profiles
	 * @return a {@link ProfileOperations} for working with profiles
	 */
	ProfileOperations profileOperations();
	
	/**
	 * API for retrieving and performing operations on companies
	 * @return a {@link CompanyOperations} for working with companies
	 */
	CompanyOperations companyOperations();
	
	/**
	 * API for sending messages and connection requests
	 * @return a {@link CommunicationOperations} for working with communication info
	 */
	CommunicationOperations communicationOperations();
	
	/**
	 * API for searching, retrieving and bookmarking jobs
	 * @return a {@link JobOperations} for working with jobs
	 */
	JobOperations jobOperations();
	
	/**
	 * API for retrieving details of groups
	 * @return a {@link GroupOperations} for working with groups
	 */
	GroupOperations groupOperations();
	
	/**
	 * Returns the underlying {@link RestOperations} object allowing for consumption of LinkedIn endpoints that may not be otherwise covered by the API binding.
	 * The RestOperations object returned is configured to include an OAuth "Authorization" header on all requests.
	 * @return a {@link RestOperations} for work against the LinkedIn API at a low-level.
	 */
	RestOperations restOperations();
	
}
