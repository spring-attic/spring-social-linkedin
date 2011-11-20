/*
 * Copyright 2010 the original author or authors.
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

/**
 * <p>
 * Interface specifying a basic set of operations for interacting with LinkedIn.
 * Implemented by {@link LinkedInTemplate}.
 * </p>
 * 
 * <p>
 * Many of the methods contained in this interface require OAuth authentication
 * with LinkedIn. When a method's description speaks of the "current user", it
 * is referring to the user for whom the access token has been issued.
 * </p>
 * 
 * @author Craig Walls
 * @author Robert Drysdale
 */
public interface LinkedIn extends ApiBinding {

	/**
	 * API for retrieving connections
	 */
	ConnectionOperations connectionOperations();
	
	/**
	 * API for retrieving and performing operations on network updates
	 */
	NetworkUpdateOperations networkUpdateOperations();
	
	/**
	 * API for retrieving and performing operations on profiles
	 */
	ProfileOperations profileOperations();
	
	/**
	 * API for retrieving and performing operations on companies
	 */
	CompanyOperations companyOperations();
	
	/**
	 * Retrieves json data from provided url
	 * url must be valid linked in url.
	 * Useful for debugging
	 * 
	 * @param url
	 * @return JSON String data
	 */
	public String getJson(String url);
	
}
