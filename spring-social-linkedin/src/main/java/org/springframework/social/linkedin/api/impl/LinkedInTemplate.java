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
import java.util.List;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.linkedin.api.CommunicationOperations;
import org.springframework.social.linkedin.api.CompanyOperations;
import org.springframework.social.linkedin.api.ConnectionOperations;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.NetworkUpdateOperations;
import org.springframework.social.linkedin.api.ProfileOperations;
import org.springframework.social.linkedin.api.impl.json.LinkedInModule;
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
	public LinkedInTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		registerLinkedInJsonModule();
		registerJsonFormatInterceptor();
		initSubApis();
	}
	
	public ConnectionOperations connectionOperations() {
		return connectionOperations;
	}
	
	public NetworkUpdateOperations networkUpdateOperations() {
		return networkUpdateOperations;
	}
	
	public ProfileOperations profileOperations() {
		return profileOperations;
	}
	
	public CompanyOperations companyOperations() {
		return companyOperations;
	}
	
	public CommunicationOperations communicationOperations() {
		return communicationOperations;
	}
	
	public String getJson(String url) {
		return getRestTemplate().getForObject(url, String.class);
	}

	// private helpers
	
	
	private void registerLinkedInJsonModule() {
		List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if(converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				objectMapper = new ObjectMapper();				
				objectMapper.registerModule(new LinkedInModule());
				objectMapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true);
				objectMapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
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
	
	private void initSubApis() {
		connectionOperations = new ConnectionTemplate(getRestTemplate());
		networkUpdateOperations = new NetworkUpdateTemplate(getRestTemplate());
		profileOperations = new ProfileTemplate(getRestTemplate(), objectMapper);
		companyOperations = new CompanyTemplate(getRestTemplate(), objectMapper);
		communicationOperations = new CommunicationTemplate(getRestTemplate());
	}
	
	private NetworkUpdateOperations networkUpdateOperations;
	
	private ProfileOperations profileOperations;
	
	private ConnectionOperations connectionOperations;
	
	private CompanyOperations companyOperations;
	
	private CommunicationOperations communicationOperations;
	
	private ObjectMapper objectMapper;
	
	static final String BASE_URL = "https://api.linkedin.com/v1/people/";
	
	private static final class JsonFormatInterceptor implements ClientHttpRequestInterceptor {
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			HttpRequest contentTypeResourceRequest = new HttpRequestDecorator(request);
			contentTypeResourceRequest.getHeaders().add("x-li-format", "json");
			return execution.execute(contentTypeResourceRequest, body);
		}
		
	}
}
