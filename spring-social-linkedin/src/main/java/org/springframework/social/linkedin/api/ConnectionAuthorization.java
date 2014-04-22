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

import java.io.Serializable;

/**
 * Carries authorization information required for connecting to another LinkedIn user by their ID.
 * Obtained via a {@link LinkedInProfile} after a user profile search.
 * 
 * @author Robert Drysdale
 * @author habuma
 */
public class ConnectionAuthorization extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;

	private final String value;
	
	public ConnectionAuthorization(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

}
