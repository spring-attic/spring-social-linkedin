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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.linkedin.api.Companies;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.Product;
import org.springframework.social.linkedin.api.Products;

public class CompanyTemplateTest extends AbstractLinkedInApiTest {
	@Test
	public void getCompany() {
		mockServer.expect(requestTo(CompanyTemplate.COMPANY_URL.replaceFirst("\\{id\\}", "/1337").replaceFirst("\\{filter\\}", "") + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("company.json", getClass()), MediaType.APPLICATION_JSON));
		Company company = linkedIn.companyOperations().getCompany(1337);
		
		assertEquals(1337, company.getId());
		assertEquals("https://feeds.feedburner.com/LinkedInBlog", company.getBlogRssUrl());
		assertEquals("C", company.getCompanyType().getCode());
		assertEquals("Public Company", company.getCompanyType().getName());
		assertEquals("LinkedIn takes your professional network online, giving you access to people, jobs and opportunities like never before. Built upon trusted connections and relationships, LinkedIn has established the world\u2019s largest and most powerful professional network. Currently, more than 135 million professionals are on LinkedIn, including executives from all five hundred of the Fortune 500 companies, as well as a wide range of household names in technology, financial services, media, consumer packaged goods, entertainment, and numerous other industries. The company is publicly held and has a diversified business model with revenues coming from user subscriptions, advertising sales and hiring solutions.", company.getDescription());
		assertEquals(1, company.getEmailDomains().size());
		assertEquals("linkedin.com", company.getEmailDomains().get(0));
		assertEquals("G", company.getEmployeeCountRange().getCode());
		assertEquals("1001-5000", company.getEmployeeCountRange().getName());
		assertEquals(2003, company.getFoundedYear());
		assertEquals("Internet", company.getIndustry());
		assertEquals(5, company.getLocations().size());
		assertEquals("Mountain View", company.getLocations().get(0).getAddress().getCity());
		assertEquals("94043", company.getLocations().get(0).getAddress().getPostalCode());
		assertEquals("2029 Stierlin Court", company.getLocations().get(0).getAddress().getStreet1());
		assertEquals("(402) 452-2320", company.getLocations().get(1).getContactInfo().getPhone1());
		assertEquals("https://media.linkedin.com/mpr/mpr/p/3/000/0c2/1d7/1894403.png", company.getLogoUrl());
		assertEquals("LinkedIn", company.getName());
		assertEquals(90110, company.getNumFollowers());
		assertEquals(8, company.getSpecialties().size());
		assertEquals("Online Professional Network", company.getSpecialties().get(0));
		assertEquals("https://media.linkedin.com/mpr/mpr/p/2/000/0fe/1b9/26acf51.png", company.getSquareLogoUrl());
		assertEquals("OPR", company.getStatus().getCode());
		assertEquals("Operating", company.getStatus().getName());
		assertEquals("NYS", company.getStockExchange().getCode());
		assertEquals("New York Stock Exchange", company.getStockExchange().getName());
		assertEquals("LNKD", company.getTicker());
		assertEquals("linkedin", company.getTwitterId());
		assertEquals("linkedin", company.getUniversalName());
		assertEquals("https://www.linkedin.com", company.getWebsiteUrl());
	}
	
	@Test
	public void search() {
		mockServer.expect(requestTo(CompanyTemplate.COMPANY_SEARCH_URL.replaceFirst("\\{keywords\\}", "spring%20j2ee") + "&oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("company_search.json", getClass()), MediaType.APPLICATION_JSON));
		Companies result = linkedIn.companyOperations().search("spring j2ee");
		
		assertEquals(10, result.getCount());
		assertEquals(0, result.getStart());
		assertEquals(88, result.getTotal());
		assertEquals(10, result.getCompanies().size());
		
		Company company = result.getCompanies().get(0);
		assertEquals("", company.getBlogRssUrl());
		assertEquals("P", company.getCompanyType().getCode());
		assertEquals("Privately Held", company.getCompanyType().getName());
		assertEquals("Rain Concert Technologies (P) Ltd, is a leading provider of Web-Mobile-Electronics convergent solutions and has capabilities that span across various domains, applications and technologies. Very strong Web-Mobile convergent solutions are at its best with Rain Concert.\r\nOur spectrum of  solutions range from  High End Web Application Development, Rich Internet Applications, iphone/ipad Applications, e-Governance solutions, Social Networking Applications, Online Learning Management Systems, Web Casting Applications, Web Based CRM & ERP Solutions, Collaborative Video Sharing Platforms, e-Commerce Applications, Online Auction & e-Commerce Portals,  to development of Games for Web & Mobile, Enterprise domain solutions. \r\nThe company has a Regional Office in Delhi and has set up offices in Saudi, Dubai and soon to launch its office in Bahrain and has strategic partners in USA, Bahrain and UAE. Aarthika Technologies, the subsidiary of Rain Concert has a strong presence in the online sales platform and continues to maintain its position as a Top Rated Company. \r\n\r\n\r\nTechnology Expertise\r\n\r\nRich Internet Applications\r\n\r\nInteractive 3D\r\nAs3, Flash, Flex\r\nAdobe AIR, Paper Vision 3D\r\nFlash Media Server\r\nWowsa Server\r\n\r\nMobile \r\n\r\nIphone, ipad,\r\nAndroid, BlackBerry, J2Me, Symbian\r\n\r\nWeb \r\n\r\nPHP, Ruby on Rail, Perl, .Net,\r\nJ2EE, Hibernete, Spring\r\nMySQl, MSSQL, Oracle\r\n\r\nElectronics\r\n\r\nUtility Electronics\r\nHigh-end Security Products\r\nConvergent Applications", company.getDescription());
		assertEquals("aarthikaindia.com", company.getEmailDomains().get(0));
		assertEquals("D", company.getEmployeeCountRange().getCode());
		assertEquals("51-200", company.getEmployeeCountRange().getName());
		assertEquals(2006, company.getFoundedYear());
		assertEquals(202421, company.getId());
		assertEquals("Information Technology and Services", company.getIndustry());
		assertEquals(1, company.getLocations().size());
		assertEquals("Trivandrum", company.getLocations().get(0).getAddress().getCity());
		assertEquals("695010", company.getLocations().get(0).getAddress().getPostalCode());
		assertEquals("Thejaswini", company.getLocations().get(0).getAddress().getStreet1());
		assertEquals("Rain Concert Technologies (P) Ltd. / Aarthika Technologies", company.getName());
		assertEquals(166, company.getNumFollowers());
		assertEquals(7, company.getSpecialties().size());
		assertEquals("Web Application Development", company.getSpecialties().get(0));
		assertEquals("OPR", company.getStatus().getCode());
		assertEquals("Operating", company.getStatus().getName());
		assertEquals("", company.getTwitterId());
		assertEquals("rain-concert-technologies-p-ltd.-aarthika-technologies", company.getUniversalName());
		assertEquals("www.rainconcert.in", company.getWebsiteUrl());
	}
	
	@Test
	public void getFollowing() {
		mockServer.expect(requestTo(CompanyTemplate.COMPANY_FOLLOW_URL + "?oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("following.json", getClass()), MediaType.APPLICATION_JSON));
		List<Company> companies = linkedIn.companyOperations().getFollowing();
		
		assertEquals(6, companies.size());
		assertEquals("amworks", companies.get(0).getName());
	}
	
	@Test
	public void getSuggestionsToFollow() {
		mockServer.expect(requestTo(CompanyTemplate.COMPANY_SUGGESTIONS_TO_FOLLOW + "?oauth2_access_token=ACCESS_TOKEN")).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("suggestions_to_follow.json", getClass()), MediaType.APPLICATION_JSON));
		List<Company> companies = linkedIn.companyOperations().getSuggestionsToFollow();
		
		assertEquals(10, companies.size());
		assertEquals("Ideo Technologies", companies.get(0).getName());
	}
	
	@Test
	public void getProducts() {
		mockServer.expect(requestTo((CompanyTemplate.PRODUCTS_URL + "&oauth2_access_token=ACCESS_TOKEN")
			.replaceFirst("\\{id\\}", "1337")
			.replaceFirst("\\{start\\}", "0")
			.replaceFirst("\\{count\\}", "5"))).andExpect(method(GET))
			.andRespond(withSuccess(new ClassPathResource("products.json", getClass()), MediaType.APPLICATION_JSON));
		Products productResult = linkedIn.companyOperations().getProducts(1337, 0, 5);
		
		assertEquals(5, productResult.getCount());
		assertEquals(0, productResult.getStart());
		assertEquals(29, productResult.getTotal());
		assertEquals(5, productResult.getProducts().size());
		
		Product p = productResult.getProducts().get(0);
		assertEquals(new Date(1288188851297l), p.getCreationTimestamp());
		assertEquals("LinkedIn Content Ads offer marketers the opportunity to distribute multiple streams of dynamic, robust content in a single ad unit. From video and blogs to Twitter and RSS feeds, these unique ad units can be targeted to a unique segment of LinkedIn professionals through our enhanced targeting capabilities. Easy to execute and available in standard 300x250 and 160x600 media sizes, content ads require minimal maintenance. Marketers can build loyal followers by exposing LinkedIn members to the full spectrum of your most valuable content.Key Features include:", p.getDescription());
		assertEquals(4, p.getFeatures().size());
		assertEquals("Engaging user experience", p.getFeatures().get(0));
		assertEquals(1353, p.getId());
		assertEquals("https://media.linkedin.com/mpr/mpr/p/1/000/07b/03f/2165a65.jpg", p.getLogoUrl());
		assertEquals("LinkedIn Content Ads", p.getName());
		assertEquals(3, p.getNumRecommendations());
		assertEquals("INT", p.getProductCategory().getCode());
		assertEquals("Internet", p.getProductCategory().getName());
		assertEquals(3, p.getRecommendations().size());
		assertEquals(160209, p.getRecommendations().get(0).getId());
		assertEquals(1353, p.getRecommendations().get(0).getProductId());
		assertEquals("CSUN", p.getRecommendations().get(0).getRecommender().getFirstName());
		assertEquals("4vmInU1H4C", p.getRecommendations().get(0).getRecommender().getId());
		assertEquals("C.", p.getRecommendations().get(0).getRecommender().getLastName());
		assertEquals("Please \"like\" our College Facebook ( https://www.facebook.com/pages/CSU-Northridge-College-of-Business-and-Economics/178294905565227 ), \"follow\" us on Tumblr ( https://cobaecsun.tumblr.com ), and \"follow\" us on Twitter ( https://twitter.com/cobaecsun ).", p.getRecommendations().get(0).getText());
		assertEquals(new Date(1319639274411l), p.getRecommendations().get(0).getTimestamp());
	}

}
