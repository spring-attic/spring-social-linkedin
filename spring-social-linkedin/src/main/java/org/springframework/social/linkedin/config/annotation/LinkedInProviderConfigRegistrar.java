/*
 * Copyright 2012 the original author or authors.
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
package org.springframework.social.linkedin.config.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.social.config.annotation.ProviderConfigRegistrarSupport;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.config.xml.UserIdSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;

/**
 * {@link ImportBeanDefinitionRegistrar} for configuring a {@link LinkedInConnectionFactory} bean and a request-scoped {@link LinkedIn} bean.
 * @author Craig Walls
 */
public class LinkedInProviderConfigRegistrar extends ProviderConfigRegistrarSupport {

	public LinkedInProviderConfigRegistrar() {
		super(EnableLinkedIn.class, LinkedInConnectionFactory.class, LinkedInApiHelper.class);
	}
	
	static class LinkedInApiHelper implements ApiHelper<LinkedIn> {
		
		private final UsersConnectionRepository usersConnectionRepository;

		private final UserIdSource userIdSource;

		private LinkedInApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
			this.usersConnectionRepository = usersConnectionRepository;
			this.userIdSource = userIdSource;		
		}

		public LinkedIn getApi() {
			if (logger.isDebugEnabled()) {
				logger.debug("Getting API binding instance for LinkedIn provider");
			}
					
			Connection<LinkedIn> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(LinkedIn.class);
			if (logger.isDebugEnabled() && connection == null) {
				logger.debug("No current connection; Returning default TwitterTemplate instance.");
			}
			return connection != null ? connection.getApi() : null;
		}
		
		private final static Log logger = LogFactory.getLog(LinkedInApiHelper.class);

	}

}
