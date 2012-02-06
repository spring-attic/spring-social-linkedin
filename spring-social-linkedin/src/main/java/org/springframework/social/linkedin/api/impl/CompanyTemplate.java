/*
 * Copyright 2012 the original author or authors.
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

import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.CompanyOperations;
import org.springframework.social.linkedin.api.ProductResult;
import org.springframework.social.linkedin.api.SearchResultCompany;
import org.springframework.web.client.RestOperations;

/**
 * Class that implements Company API for searching for and getting Companies
 * @author Robert Drysdale
 *
 */
public class CompanyTemplate extends AbstractTemplate implements CompanyOperations {
	private final RestOperations restOperations;
	private final ObjectMapper objectMapper;
	
	public CompanyTemplate(RestOperations RestOperations, ObjectMapper objectMapper) {
		this.restOperations = RestOperations;
		this.objectMapper = objectMapper;
	}
	
	public Company getCompany(int id) {
		return restOperations.getForObject(COMPANY_URL, Company.class, "/" + id, "");
	}
	
	public Company getCompanyByUniversalName(String name) {
		return restOperations.getForObject(COMPANY_URL, Company.class, "/universal-name=" + name, "");
	}
	
	public List<Company> getCompaniesByEmailDomain(String domain) {
		String[] params = new String[] { "", "email-domain=" + domain};
		JsonNode node = restOperations.getForObject(expand(COMPANY_URL, params, false), JsonNode.class);
		
		try {
			return objectMapper.readValue(node.path("values"), new TypeReference<List<Company>>(){});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public SearchResultCompany search(String keywords) {
		JsonNode node = restOperations.getForObject(COMPANY_SEARCH_URL, JsonNode.class, keywords);
		try {
			return objectMapper.readValue(node.path("companies"), new TypeReference<SearchResultCompany>(){});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Company> getFollowing() {
		JsonNode node = restOperations.getForObject(COMPANY_FOLLOW_URL, JsonNode.class);
		try {
			return objectMapper.readValue(node.path("values"), new TypeReference<List<Company>>(){});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Company> getSuggestionsToFollow() {
		JsonNode node = restOperations.getForObject(COMPANY_SUGGESTIONS_TO_FOLLOW, JsonNode.class);
		try {
			return objectMapper.readValue(node.path("values"), new TypeReference<List<Company>>(){});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void startFollowingCompany(int id) {
		restOperations.postForLocation(COMPANY_FOLLOW_START_STOP_URL, Collections.singletonMap("id", id));
	}
	
	public void stopFollowingCompany(int id) {
		restOperations.delete(COMPANY_FOLLOW_START_STOP_URL, id);
	}
	
	public ProductResult getProducts(int companyId, int start, int count) {
		return restOperations.getForObject(PRODUCTS_URL, ProductResult.class, companyId, start, count);
	}
	
	public static final String BASE_URL = "https://api.linkedin.com/v1/";
	public static final String COMPANY_FIELDS = "(id,name,universal-name,email-domains,company-type,ticker,website-url,industry,status,logo-url,square-logo-url,blog-rss-url,twitter-id,employee-count-range,specialties,locations,description,stock-exchange,founded-year,end-year,num-followers)";
	public static final String COMPANY_URL = BASE_URL + "companies{id}:" + COMPANY_FIELDS + "?{filter}";
	public static final String COMPANY_SEARCH_URL = BASE_URL + "company-search:(companies:" + COMPANY_FIELDS + ")?keywords={keywords}";
	public static final String COMPANY_FOLLOW_URL = BASE_URL + "people/~/following/companies:" + COMPANY_FIELDS;
	public static final String COMPANY_FOLLOW_START_STOP_URL = BASE_URL + "people/~/following/companies/id={id}";
	public static final String COMPANY_SUGGESTIONS_TO_FOLLOW = BASE_URL + "people/~/suggestions/to-follow/companies:" + COMPANY_FIELDS;
	
	public static final String PRODUCT_FIELDS="(id,name,type,creation-timestamp,logo-url,description,features,video:(title,url),product-deal:(title,url,text),sales-persons,num-recommendations,recommendations:(recommender,id,product-id,text,reply,timestamp,likes:(timestamp,person)),product-category,website-url,disclaimer)";
	public static final String PRODUCTS_URL = BASE_URL + "companies/{id}/products:" + PRODUCT_FIELDS +"?start={start}&count={count}";
	
}
