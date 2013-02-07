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

import java.util.Date;
import java.util.List;

/**
 * Model class representing an update action.
 * 
 * @author Robert Drysdale
 */
public class UpdateAction extends LinkedInNetworkUpdate {

	private static final long serialVersionUID = 1L;
	
	private String action;

	private List<Comment> updateComments;
	
	public UpdateAction(Date timestamp, String updateKey, UpdateType updateType) {
		super(timestamp, updateKey, updateType);
	}

	public String getAction() {
		return action;
	}

	public List<Comment> getUpdateComments() {
		return updateComments;
	}

}
