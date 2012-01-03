package org.springframework.social.linkedin.api.impl;

import static org.springframework.social.linkedin.api.impl.LinkedInTemplate.BASE_URL;

import java.util.List;

import org.springframework.social.linkedin.api.ConnectionOperations;
import org.springframework.social.linkedin.api.LinkedInConnections;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.NetworkStatistics;
import org.springframework.web.client.RestTemplate;

/**
 * Class that implements Connection API
 * 
 * @author Robert Drysdale
 *
 */
public class ConnectionTemplate implements ConnectionOperations {
	private final RestTemplate restTemplate;
	
	public ConnectionTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public List<LinkedInProfile> getConnections() {
		LinkedInConnections connections = restTemplate.getForObject(CONNECTIONS_URL, LinkedInConnections.class);
		return connections.getConnections();
	}
	
	public NetworkStatistics getNetworkStatistics(){
		return restTemplate.getForObject(STATISTICS_URL,  NetworkStatistics.class);
	}
	
	static final String CONNECTIONS_URL = BASE_URL + "~/connections?format=json";
	
	static final String STATISTICS_URL = BASE_URL + "~/network/network-stats?format=json";
}
