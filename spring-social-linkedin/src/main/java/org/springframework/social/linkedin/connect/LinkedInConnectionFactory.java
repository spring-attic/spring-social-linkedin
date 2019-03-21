/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.linkedin.api.LinkedIn;

/**
 * LinkedIn ConnectionFactory implementation.
 * @author Keith Donald
 */
public class LinkedInConnectionFactory extends OAuth2ConnectionFactory<LinkedIn>{

	public LinkedInConnectionFactory(String consumerKey, String consumerSecret) {
		super("linkedin", new LinkedInServiceProvider(consumerKey, consumerSecret), new LinkedInAdapter());
	}

}
