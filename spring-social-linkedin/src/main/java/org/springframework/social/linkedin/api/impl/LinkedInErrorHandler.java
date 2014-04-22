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
package org.springframework.social.linkedin.api.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LinkedInErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		Map<String, Object> errorDetails = extractErrorDetailsFromResponse(response);
		String message = (String) errorDetails.get("message");
		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.equals(HttpStatus.UNAUTHORIZED)) {
			throw new NotAuthorizedException("linkedIn", message);
		} else if (statusCode.equals(HttpStatus.FORBIDDEN)) {
			if (message.contains("Throttle")) {
				throw new RateLimitExceededException("linkedin");
			} else {
				throw new InsufficientPermissionException("linkedin");
			}
		} else if (statusCode.equals(HttpStatus.NOT_FOUND)) {
			throw new ResourceNotFoundException("linkedin", message);
		}
		
		handleUncategorizedError(response);
	}
	
	private void handleUncategorizedError(ClientHttpResponse response) {
		try {
			super.handleError(response);
		} catch (Exception e) {
			throw new UncategorizedApiException("linkedin", "", e);
		}
	}
	
	private Map<String, Object> extractErrorDetailsFromResponse(ClientHttpResponse response) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		try {
			return mapper.<Map<String, Object>>readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonParseException e) {
			return Collections.emptyMap();
		}
	}

}
