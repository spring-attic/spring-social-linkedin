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
package org.springframework.social.linkedin.api.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Craig Walls
 */
class JsonFormatHeaderRequestFactory implements ClientHttpRequestFactory {

	private final ClientHttpRequestFactory delegate;

	public JsonFormatHeaderRequestFactory(ClientHttpRequestFactory delegate) {
		this.delegate = delegate;
	}

	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		return new JsonFormatWrappedRequest(delegate.createRequest(uri, httpMethod));
	}

	private static class JsonFormatWrappedRequest implements ClientHttpRequest {

		private final ClientHttpRequest delegate;

		private ByteArrayOutputStream bodyOutputStream;

		public JsonFormatWrappedRequest(ClientHttpRequest delegate) {
			this.delegate = delegate;
			this.bodyOutputStream = new ByteArrayOutputStream();
		}

		public ClientHttpResponse execute() throws IOException {
			byte[] bufferedOutput = bodyOutputStream.toByteArray();
			delegate.getBody().write(bufferedOutput);
			delegate.getHeaders().set("x-li-format", "json");
			return delegate.execute();
		}

		public URI getURI() {
			return delegate.getURI();
		}

		public HttpMethod getMethod() {
			return delegate.getMethod();
		}

		public HttpHeaders getHeaders() {
			return delegate.getHeaders();
		}

		public OutputStream getBody() throws IOException {
			return bodyOutputStream;
		}

		@Override
		public String getMethodValue() {
			return delegate.getMethodValue();
		}

	}

}
