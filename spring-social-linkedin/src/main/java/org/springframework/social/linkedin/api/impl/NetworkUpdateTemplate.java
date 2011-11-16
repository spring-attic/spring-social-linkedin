package org.springframework.social.linkedin.api.impl;

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.BASE_URL;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.client.RestTemplate;

public class NetworkUpdateTemplate implements NetworkUpdateOperations {
	
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
	
	public NetworkUpdateTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
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
		Comments comments = restTemplate.getForObject(UPDATE_COMMENTS_URL, Comments.class, updateKey);
		return comments.getComments();
	}
	
	public void createNetworkUpdate(String update) {
		Map<String,String> activity = new HashMap<String, String>();
		activity.put("contentType", "linkedin-html");
		activity.put("body", update);
		restTemplate.put(ACTIVITY_URL, activity);
	}
	
	public CurrentShare getCurrentShare() {
		return restTemplate.getForObject(CURRENT_SHARE_URL, UpdateContentShare.class).getCurrentShare();
	}
	
	public URI share(NewShare share) {
		URI uri = restTemplate.postForLocation(SHARE_URL, share);
		return uri;
	}
	
	public void likeNetworkUpdate(String updateKey) {
		restTemplate.put(UPDATE_IS_LIKED_URL, Boolean.TRUE, updateKey);
	}
	
	public void unlikeNetworkUpdate(String updateKey) {
		restTemplate.put(UPDATE_IS_LIKED_URL, Boolean.FALSE, updateKey);
	}
	
	public void commentOnNetworkUpdate(String updateKey, String comment) {
		restTemplate.put(UPDATE_COMMENTS_URL, 
				Collections.singletonMap("comment", comment), updateKey);
	}
	
	public List<LinkedInProfile> getNetworkUpdateLikes(String updateKey) {
		Likes likes = restTemplate.getForObject(UPDATE_LIKES_URL, Likes.class, updateKey);
		return likes.getLikes();
	}
	
	public String getNetworkUpdatesJson(NetworkUpdateParameters parameters) {
		return getNetworkUpdates(parameters, String.class);
	}
	
	private <T> T  getNetworkUpdates(NetworkUpdateParameters parameters, Class<T> responseType) {
		return restTemplate.getForObject(expand(UPDATES_URL, parameters), responseType);
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
	public static URI expand(String url, NetworkUpdateParameters parameters) {
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
		
		Matcher matcher = NAMES_PATTERN.matcher(url);
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		while (matcher.find()) {
			Object uriVariable = variables[i++];
			String replacement = Matcher.quoteReplacement(uriVariable != null ? uriVariable.toString() : "");
			String key = matcher.group();
			if (key.charAt(1) == '&' && replacement != null && replacement.length() > 0) {
				key = key.substring(1, key.length()-1);
				matcher.appendReplacement(buffer, key + '=' + replacement);
			}
			else {
				matcher.appendReplacement(buffer, replacement);
			}
		}
		matcher.appendTail(buffer);
		try {
			return new URI(buffer.toString());
		}
		catch (URISyntaxException ex) {
			throw new IllegalArgumentException("Could not create URI from [" + buffer + "]: " + ex, ex);
		}
	}
	
	static final String UPDATES_URL = BASE_URL + "{id}/network/updates?format=json{&count}{&start}{&scope}{type}{&before}{&after}{&show-hidden-members}";

	static final String UPDATE_COMMENTS_URL = BASE_URL + "~/network/updates/key={key}/update-comments?format=json";
	
	static final String UPDATE_LIKES_URL = BASE_URL + "~/network/updates/key={key}/likes?format=json";
	
	static final String UPDATE_IS_LIKED_URL = BASE_URL + "~/network/updates/key={key}/is-liked?format=json";
	
	static final String ACTIVITY_URL = BASE_URL +  "~/person-activities";
	
	static final String CURRENT_SHARE_URL = BASE_URL + "~:(current-share)";
	
	static final String SHARE_URL = BASE_URL + "~/shares";
	
	public static final int DEFAULT_START  = 0;
	
	public static final int DEFAULT_COUNT = 10;
	
	private static final String UPDATE_TYPE_ALL_STRING;
	
	/** Captures URI template variable names. */
	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");
	
	private final RestTemplate restTemplate;
	
	
}
