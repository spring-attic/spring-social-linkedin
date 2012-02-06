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
package org.springframework.social.linkedin.api.impl;

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.*;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.linkedin.api.Comment;
import org.springframework.social.linkedin.api.Comments;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.Likes;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdates;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.NetworkUpdateOperations;
import org.springframework.social.linkedin.api.NetworkUpdateParameters;
import org.springframework.social.linkedin.api.NewShare;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateTypeInput;
import org.springframework.web.client.RestOperations;

/**
 * Class that impelements API for retrieving Network Updates and
 * performing various actions on them
 * 
 * @author Robert Drysdale
 *
 */
class NetworkUpdateTemplate extends AbstractTemplate implements NetworkUpdateOperations {
	
	static {
		/*
		 * The UPDATE_TYPE_ALL is required because by default
		 * the linked in Network Updates API  does not return VIRL
		 * (Viral) Updates such as User Liking or Commenting on
		 * another Users post
		 */
		
		StringBuffer b = new StringBuffer();
		for (UpdateTypeInput t : UpdateTypeInput.values()) {
			b.append("&type=").append(t);
		}
		UPDATE_TYPE_ALL_STRING = b.toString();
	}
	
	public NetworkUpdateTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public List<LinkedInNetworkUpdate> getNetworkUpdates() {
		NetworkUpdateParameters parameters = new NetworkUpdateParameters(
				null,
				false,
				DEFAULT_START,
				DEFAULT_COUNT,
				null,
				null,
				true,
				false,
				Collections.<UpdateTypeInput>emptyList());
		return getNetworkUpdates(parameters);
	}
	
	public List<LinkedInNetworkUpdate> getNetworkUpdates(int start, int count) {
		NetworkUpdateParameters parameters = new NetworkUpdateParameters(
				null,
				false,
				start,
				count,
				null,
				null,
				true,
				false,
				Collections.<UpdateTypeInput>emptyList());
		return getNetworkUpdates(parameters);
	}
	
	public List<LinkedInNetworkUpdate> getNetworkUpdates(NetworkUpdateParameters parameters) {
		return getNetworkUpdates(parameters, LinkedInNetworkUpdates.class).getUpdates();
	}
	
	public List<Comment> getNetworkUpdateComments(String updateKey) {
		Comments comments = restOperations.getForObject(UPDATE_COMMENTS_URL, Comments.class, updateKey);
		return comments.getComments();
	}
	
	public void createNetworkUpdate(String update) {
		Map<String,String> activity = new HashMap<String, String>();
		activity.put("contentType", "linkedin-html");
		activity.put("body", update);
		restOperations.put(ACTIVITY_URL, activity);
	}
	
	public CurrentShare getCurrentShare() {
		return restOperations.getForObject(CURRENT_SHARE_URL, UpdateContentShare.class).getCurrentShare();
	}
	
	public URI share(NewShare share) {
		URI uri = restOperations.postForLocation(SHARE_URL, share);
		return uri;
	}
	
	public void likeNetworkUpdate(String updateKey) {
		restOperations.put(UPDATE_IS_LIKED_URL, Boolean.TRUE, updateKey);
	}
	
	public void unlikeNetworkUpdate(String updateKey) {
		restOperations.put(UPDATE_IS_LIKED_URL, Boolean.FALSE, updateKey);
	}
	
	public void commentOnNetworkUpdate(String updateKey, String comment) {
		restOperations.put(UPDATE_COMMENTS_URL, 
				Collections.singletonMap("comment", comment), updateKey);
	}
	
	public List<LinkedInProfile> getNetworkUpdateLikes(String updateKey) {
		Likes likes = restOperations.getForObject(UPDATE_LIKES_URL, Likes.class, updateKey);
		return likes.getLikes();
	}
	
	public String getNetworkUpdatesJson(NetworkUpdateParameters parameters) {
		return getNetworkUpdates(parameters, String.class);
	}
	
	private <T> T  getNetworkUpdates(NetworkUpdateParameters parameters, Class<T> responseType) {
		return restOperations.getForObject(expand(UPDATES_URL, parameters), responseType);
	}
	
	/*
	 * Added this as UriTemplate does not fully support uri templates
	 * as per spec @ http://tools.ietf.org/html/draft-gregorio-uritemplate-07
	 * 
	 * Specifically {&start} should expand to start=VALUE or blank if not present but it seems
	 * to just expand to VALUE as if it was {start}.  & is ignored
	 * 
	 * Also don't want to escape & or = chars
	 */
	private URI expand(String url, NetworkUpdateParameters parameters) {
		String type = null;
		if (parameters.getUpdateAll()) {
			type = UPDATE_TYPE_ALL_STRING;
		}
		else if (parameters.getUpdateTypes() != null && parameters.getUpdateTypes().size() > 0) {
			StringBuffer b = new StringBuffer();
			for (UpdateTypeInput t : parameters.getUpdateTypes() ) {
				b.append("&type=").append(t);
			}
			type = b.toString();
		}
		
		Object[] variables = new Object[] {
				parameters.getUser() == null ? "~" : "id=" + parameters.getUser(),
				parameters.getRecordCount(),
				parameters.getRecordStart(),
				parameters.getSelf() ? "self" : null,
				type,
				parameters.getRecordsBefore() == null ? null : parameters.getRecordsBefore().getTime(),
				parameters.getRecordsAfter() == null ? null : parameters.getRecordsAfter().getTime(),
				parameters.getShowHidden() ? "true" : null
		};
		
		return expand(url, variables, false);
	}
	
	static final String UPDATES_URL = BASE_URL + "{id}/network/updates?{&count}{&start}{&scope}{type}{&before}{&after}{&show-hidden-members}&format=json";

	static final String UPDATE_COMMENTS_URL = BASE_URL + "~/network/updates/key={key}/update-comments?format=json";
	
	static final String UPDATE_LIKES_URL = BASE_URL + "~/network/updates/key={key}/likes?format=json";
	
	static final String UPDATE_IS_LIKED_URL = BASE_URL + "~/network/updates/key={key}/is-liked?format=json";
	
	static final String ACTIVITY_URL = BASE_URL +  "~/person-activities";
	
	static final String CURRENT_SHARE_URL = BASE_URL + "~:(current-share)";
	
	static final String SHARE_URL = BASE_URL + "~/shares";
	
	public static final int DEFAULT_START  = 0;
	
	public static final int DEFAULT_COUNT = 10;
	
	private static final String UPDATE_TYPE_ALL_STRING;
	
	private final RestOperations restOperations;

}
