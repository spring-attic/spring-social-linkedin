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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LinkedInErrorHandler extends DefaultResponseErrorHandler {

	private final static Log logger = LogFactory.getLog(LinkedInErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		Map<String, Object> errorDetails = extractErrorDetailsFromResponse(response);
		String message = (String) errorDetails.get("message");
		if(message == null) {
			message = "";
		}
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
		byte[] in = toByteArray(response.getBody());
		try {
			return mapper.<Map<String, Object>>readValue(in, new TypeReference<Map<String, Object>>() {});
		} catch (JsonParseException e) {
			//try and read the message out the body, as XML
			String body = new String(in);
			return Collections.singletonMap("message", (Object) getMessageFromXml(body));
		}
	}

	private static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		if(is != null) {
			int position;
			byte[] data = new byte[1024];
			while ((position = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, position);
			}
			buffer.flush();
		}

		return buffer.toByteArray();
	}

	private String getMessageFromXml(String xml) throws IOException {
		String message = ""; // default
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
			NodeList nodeList = doc.getElementsByTagName("message");
			if(nodeList.getLength() > 0) {
				message = nodeList.item(0).getTextContent();
			}
		} catch (ParserConfigurationException pce) {
			throw new IllegalStateException("XML parser is incorrectly configured", pce);
		} catch (SAXException se) {
			logger.warn("failed to parse response body as XML", se);
		}
		return message;
	}

}
