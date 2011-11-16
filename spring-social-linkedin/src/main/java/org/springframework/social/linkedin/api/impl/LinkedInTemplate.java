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
package org.springframework.social.linkedin.api.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.linkedin.api.Comment;
import org.springframework.social.linkedin.api.Comments;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.Likes;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInConnections;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdates;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.NewShare;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateTypeInput;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.social.support.HttpRequestDecorator;

/**
 * This is the central class for interacting with LinkedIn.
 * <p>
 * Greenhouse operations require OAuth authentication with the server.
 * Therefore, LinkedInTemplate must be constructed with the minimal information
 * required to sign requests with and OAuth 1 Authorization header.
 * </p>
 * @author Craig Walls
 */
public class LinkedInTemplate extends AbstractOAuth1ApiBinding implements LinkedIn {

	/**
	 * Creates a new LinkedInTemplate given the minimal amount of information needed to sign requests with OAuth 1 credentials.
	 * @param consumerKey the application's API key
	 * @param consumerSecret the application's API secret
	 * @param accessToken an access token acquired through OAuth authentication with LinkedIn
	 * @param accessTokenSecret an access token secret acquired through OAuth authentication with LinkedIn
	 */
	
	private static final String UPDATE_TYPE_ALL_STRING;
	
	public static final int DEFAULT_START  = 0;
	
	public static final int DEFAULT_COUNT = 10;
	
	/** Captures URI template variable names. */
	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");
	
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
	
	public LinkedInTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		registerLinkedInJsonModule();
		registerJsonFormatInterceptor();
	}
	
	public String getProfileId() {
		return getUserProfile().getId();
	}

	public String getProfileUrl() {
		return getUserProfile().getPublicProfileUrl();
	}

	public LinkedInProfile getUserProfile() {
		return getRestTemplate().getForObject(PROFILE_URL, LinkedInProfile.class);
	}
	
	public List<LinkedInNetworkUpdate> getNetworkUpdates() {
		LinkedIn.LinkedInParameters parameters = new LinkedIn.LinkedInParameters(
				null,
				false,
				0,
				400,
				null,
				null,
				true,
				false,
				Collections.<UpdateTypeInput>emptyList());
		return getNetworkUpdates(parameters);
	}
	
	public List<LinkedInNetworkUpdate> getNetworkUpdates(int start, int count) {
		LinkedIn.LinkedInParameters parameters = new LinkedIn.LinkedInParameters(
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
	
	public List<LinkedInNetworkUpdate> getNetworkUpdates(LinkedIn.LinkedInParameters parameters) {
		return getNetworkUpdates(parameters, LinkedInNetworkUpdates.class).getUpdates();
	}
	
	public List<Comment> getNetworkUpdateComments(String updateKey) {
		Comments comments = getRestTemplate().getForObject(UPDATE_COMMENTS_URL, Comments.class, updateKey);
		return comments.getComments();
	}
	
	public void createNetworkUpdate(String update) {
		Map<String,String> activity = new HashMap<String, String>();
		activity.put("contentType", "linkedin-html");
		activity.put("body", update);
		getRestTemplate().put(ACTIVITY_URL, activity);
	}
	
	public CurrentShare getCurrentShare() {
		return getRestTemplate().getForObject(CURRENT_SHARE_URL, UpdateContentShare.class).getCurrentShare();
	}
	
	public URI share(NewShare share) {
		URI uri = getRestTemplate().postForLocation(SHARE_URL, share);
		return uri;
	}
	
	public void likeNetworkUpdate(String updateKey) {
		getRestTemplate().put(UPDATE_IS_LIKED_URL, Boolean.TRUE, updateKey);
	}
	
	public void unlikeNetworkUpdate(String updateKey) {
		getRestTemplate().put(UPDATE_IS_LIKED_URL, Boolean.FALSE, updateKey);
	}
	
	public void commentOnNetworkUpdate(String updateKey, String comment) {
		getRestTemplate().put(UPDATE_COMMENTS_URL, 
				Collections.singletonMap("comment", comment), updateKey);
	}
	
	public List<LinkedInProfile> getNetworkUpdateLikes(String updateKey) {
		Likes likes = getRestTemplate().getForObject(UPDATE_LIKES_URL, Likes.class, updateKey);
		return likes.getLikes();
	}
	
	public String getNetworkUpdatesJson(LinkedIn.LinkedInParameters parameters) {
		return getNetworkUpdates(parameters, String.class);
	}
	
	public String getJson(String url) {
		return getRestTemplate().getForObject(url, String.class);
	}

	public List<LinkedInProfile> getConnections() {
		LinkedInConnections connections = getRestTemplate().getForObject(CONNECTIONS_URL, LinkedInConnections.class);
		return connections.getConnections();
	}

	// private helpers
	private <T> T  getNetworkUpdates(LinkedIn.LinkedInParameters parameters, Class<T> responseType) {
		return getRestTemplate().getForObject(expand(UPDATES_URL, parameters), responseType);
	}
	
	
	private void registerLinkedInJsonModule() {
		List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if(converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = new ObjectMapper();				
				objectMapper.registerModule(new LinkedInModule());
				objectMapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true);
				objectMapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}
	
	/*
	 * Have to register custom interceptor to
	 * set  "x-li-format: "json" header as
	 * otherwise we appear to get error from linkedin
	 * which suggests its expecting xml rather than json.
	 * API appears to ignore Content-Type header
	 */
	private void registerJsonFormatInterceptor() {
		List<ClientHttpRequestInterceptor> interceptors = getRestTemplate().getInterceptors();
		interceptors.add(new JsonFormatInterceptor());
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
	private URI expand(String url, LinkedIn.LinkedInParameters parameters) {
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
	
	static final String BASE_URL = "https://api.linkedin.com/v1/people/";

	static final String PROFILE_URL = BASE_URL + "~:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json";

	static final String UPDATES_URL = BASE_URL + "{id}/network/updates?format=json{&count}{&start}{&scope}{type}{&before}{&after}{&show-hidden-members}";
	
	static final String CONNECTIONS_URL = BASE_URL + "~/connections?format=json";
	
	static final String UPDATE_COMMENTS_URL = BASE_URL + "~/network/updates/key={key}/update-comments?format=json";
	
	static final String UPDATE_LIKES_URL = BASE_URL + "~/network/updates/key={key}/likes?format=json";
	
	static final String UPDATE_IS_LIKED_URL = BASE_URL + "~/network/updates/key={key}/is-liked?format=json";
	
	static final String ACTIVITY_URL = BASE_URL +  "~/person-activities";
	
	static final String CURRENT_SHARE_URL = BASE_URL + "~:(current-share)";
	
	static final String SHARE_URL = BASE_URL + "~/shares";
	
	private static final class JsonFormatInterceptor implements ClientHttpRequestInterceptor {
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			HttpRequest contentTypeResourceRequest = new HttpRequestDecorator(request);
			contentTypeResourceRequest.getHeaders().add("x-li-format", "json");
			return execution.execute(contentTypeResourceRequest, body);
		}
		
	}
}
