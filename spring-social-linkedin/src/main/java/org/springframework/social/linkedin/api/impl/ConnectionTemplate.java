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

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.*;

import java.net.URI;
import java.util.List;

import org.springframework.social.linkedin.api.ConnectionOperations;
import org.springframework.social.linkedin.api.LinkedInConnections;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.NetworkStatistics;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestOperations;

/**
 * Class that implements Connection API
 * 
 * @author Robert Drysdale
 */
class ConnectionTemplate implements ConnectionOperations {

	private final RestOperations restOperations;
	
	public ConnectionTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public List<LinkedInProfile> getConnections() {
		LinkedInConnections connections = restOperations.getForObject(URIBuilder.fromUri(CONNECTIONS_URL).build(), LinkedInConnections.class);
		return connections.getConnections();
	}

	public List<LinkedInProfile> getConnections(int start, int count) {
		URI uri = URIBuilder.fromUri(CONNECTIONS_URL)
					.queryParam("start", String.valueOf(start))
					.queryParam("count", String.valueOf(count)).build();
		LinkedInConnections connections = restOperations.getForObject(uri, LinkedInConnections.class);
		return connections.getConnections();
	}

	public NetworkStatistics getNetworkStatistics(){
		return restOperations.getForObject(STATISTICS_URL,  NetworkStatistics.class);
	}
	
	static final String CONNECTIONS_URL = BASE_URL + "~/connections?format=json";
	
	static final String STATISTICS_URL = BASE_URL + "~/network/network-stats?format=json";

}
