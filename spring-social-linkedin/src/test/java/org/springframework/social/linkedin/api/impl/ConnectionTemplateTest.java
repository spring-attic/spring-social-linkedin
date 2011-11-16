package org.springframework.social.linkedin.api.impl;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.linkedin.api.LinkedInProfile;

public class ConnectionTemplateTest extends AbstractLinkedInApiTest {
	@Test
	public void getConnections() {
		mockServer.expect(requestTo("https://api.linkedin.com/v1/people/~/connections?format=json")).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("testdata/connections.json", getClass()), responseHeaders));
		List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
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
