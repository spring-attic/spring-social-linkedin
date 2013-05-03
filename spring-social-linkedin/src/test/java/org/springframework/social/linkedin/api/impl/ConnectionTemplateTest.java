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
package org.springframework.social.linkedin.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.NetworkStatistics;

public class ConnectionTemplateTest extends AbstractLinkedInApiTest {

	@Test
	public void getConnections() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/connections?format=json&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("testdata/connections.json", getClass()), MediaType.APPLICATION_JSON));
		List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
		assertConnections(connections);
	}

	@Test
	public void getConnections_withStartAndCount() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/connections?format=json&start=10&count=20&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("testdata/connections.json", getClass()), MediaType.APPLICATION_JSON));
		List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections(10, 20);
		assertConnections(connections);
	}

	@Test
	public void getStatistics() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/network/network-stats?format=json&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("testdata/statistics.json", getClass()), MediaType.APPLICATION_JSON));
		
		NetworkStatistics stats = linkedIn.connectionOperations().getNetworkStatistics();
		
		assertEquals(189, stats.getFirstDegreeCount());
		assertEquals(50803, stats.getSecondDegreeCount());
	}
	
	private void assertConnections(List<LinkedInProfile> connections) {
		assertEquals(4, connections.size());
		assertProfile(connections.get(0), "kR0lnX1ll8", "SpringSource Cofounder", "Keith", "Donald", "Computer Software",
				"http://www.linkedin.com/profile?viewProfile=&key=2526541&authToken=61Sm&authType=name&trk=api*a121026*s129482*");
		assertProfile(connections.get(1), "VRcwcqPCtP", "GM, SpringSource and SVP, Middleware at VMware", "Rod",
				"Johnson",
				"Computer Software",
				"http://www.linkedin.com/profile?viewProfile=&key=210059&authToken=3hU1&authType=name&trk=api*a121026*s129482*");
		assertProfile(connections.get(2), "Ia7uR1OmDB", "Spring and AOP expert; author AspectJ in Action", "Ramnivas",
				"Laddad", "Computer Software",
				"http://www.linkedin.com/profile?viewProfile=&key=208994&authToken=P5K9&authType=name&trk=api*a121026*s129482*");
		assertProfile(connections.get(3), "gKEMq4CMdl", "Head of Groovy Development at SpringSource", "Guillaume",
				"Laforge", "Information Technology and Services",
				"http://www.linkedin.com/profile?viewProfile=&key=822306&authToken=YmIW&authType=name&trk=api*a121026*s129482*");
	}

}
