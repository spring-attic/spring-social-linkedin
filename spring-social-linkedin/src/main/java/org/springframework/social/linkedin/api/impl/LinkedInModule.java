/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.social.linkedin.api.impl;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.LinkedInConnections;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdates;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.MemberGroup;
import org.springframework.social.linkedin.api.PersonActivity;
import org.springframework.social.linkedin.api.Recommendation;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentConnection;
import org.springframework.social.linkedin.api.UpdateContentFollow;
import org.springframework.social.linkedin.api.UpdateContentGroup;
import org.springframework.social.linkedin.api.UpdateContentPersonActivity;
import org.springframework.social.linkedin.api.UpdateContentRecommendation;
import org.springframework.social.linkedin.api.UpdateContentStatus;

/**
 * Jackson module for registering mixin annotations against LinkedIn model classes.
 */
class LinkedInModule extends SimpleModule {

	public LinkedInModule() {
		super("LinkedInModule", new Version(1, 0, 0, null));
	}
	
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(LinkedInConnections.class, LinkedInConnectionsMixin.class);
		context.setMixInAnnotations(LinkedInProfile.class, LinkedInProfileMixin.class);
		context.setMixInAnnotations(MemberGroup.class, MemberGroupMixin.class);
		context.setMixInAnnotations(Recommendation.class, RecommendationMixin.class);
		context.setMixInAnnotations(PersonActivity.class, PersonActivityMixin.class);
		context.setMixInAnnotations(Company.class, CompanyMixin.class);
		
		context.setMixInAnnotations(LinkedInNetworkUpdate.class, LinkedInNetworkUpdateMixin.class);
		context.setMixInAnnotations(LinkedInNetworkUpdates.class, LinkedInNetworkUpdatesMixin.class);
		
		context.setMixInAnnotations(UpdateContent.class, UpdateContentMixin.class);
		context.setMixInAnnotations(UpdateContentConnection.class, UpdateContentConnectionMixin.class);
		context.setMixInAnnotations(UpdateContentStatus.class, UpdateContentStatusMixin.class);
		context.setMixInAnnotations(UpdateContentGroup.class, UpdateContentGroupMixin.class);
		context.setMixInAnnotations(UpdateContentRecommendation.class, UpdateContentRecommendationMixin.class);
		context.setMixInAnnotations(UpdateContentPersonActivity.class, UpdateContentPersonActivityMixin.class);
		context.setMixInAnnotations(UpdateContentFollow.class, UpdateContentFollowMixin.class);
		
	}

}
