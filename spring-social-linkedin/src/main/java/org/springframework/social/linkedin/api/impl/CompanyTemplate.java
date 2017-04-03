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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.linkedin.api.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class that implements Company API for searching for and getting Companies
 * 
 * @author Robert Drysdale
 */
class CompanyTemplate extends AbstractTemplate implements CompanyOperations {

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
			return objectMapper.reader(new TypeReference<List<Company>>(){}).readValue(node.path("values"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Companies search(String keywords) {
		JsonNode node = restOperations.getForObject(COMPANY_SEARCH_URL, JsonNode.class, keywords);
		try {
			return objectMapper.reader(new TypeReference<Companies>(){}).readValue(node.path("companies"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Company> getFollowing() {
		JsonNode node = restOperations.getForObject(COMPANY_FOLLOW_URL, JsonNode.class);
		try {
			return objectMapper.reader(new TypeReference<List<Company>>(){}).readValue(node.path("values"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Company> getSuggestionsToFollow() {
		JsonNode node = restOperations.getForObject(COMPANY_SUGGESTIONS_TO_FOLLOW, JsonNode.class);
		try {
			return objectMapper.reader(new TypeReference<List<Company>>(){}).readValue(node.path("values"));
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
	
	public Products getProducts(int companyId, int start, int count) {
		return restOperations.getForObject(PRODUCTS_URL, Products.class, companyId, start, count);
	}

    public Companies getCompanies() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("format", "json");
        params.put("is-company-admin", "true");

        try {
            return restOperations.getForObject(COMPANIES_URL, Companies.class, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Share postShareForCompany(NewShare share, String companyId) {
        if(StringUtils.isEmpty(companyId)){
            throw new IllegalArgumentException("CompanyId must not be empty");
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", companyId);
        params.put("format", "json");
        try {
            return restOperations.postForObject(COMPANY_SHARE_POST_URL, share, Share.class, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getBaseUrl() {
	    return BASE_URL;
    }

	public static final String BASE_URL = "https://api.linkedin.com/v1/";
	public static final String COMPANY_FIELDS = "(id,name,universal-name,email-domains,company-type,ticker,website-url,industry,status,logo-url,square-logo-url,blog-rss-url,twitter-id,employee-count-range,specialties,locations,description,stock-exchange,founded-year,end-year,num-followers)";
	public static final String COMPANY_URL = BASE_URL + "companies{id}:" + COMPANY_FIELDS + "?{filter}";
	public static final String COMPANY_SEARCH_URL = BASE_URL + "company-search:(companies:" + COMPANY_FIELDS + ")?keywords={keywords}";
	public static final String COMPANY_FOLLOW_URL = BASE_URL + "people/~/following/companies:" + COMPANY_FIELDS;
	public static final String COMPANY_FOLLOW_START_STOP_URL = BASE_URL + "people/~/following/companies/id={id}";
	public static final String COMPANY_SUGGESTIONS_TO_FOLLOW = BASE_URL + "people/~/suggestions/to-follow/companies:" + COMPANY_FIELDS;
	public static final String COMPANIES_URL = BASE_URL + "companies"+"?format={format}&is-company-admin={is-company-admin}";
	public static final String COMPANY_SHARE_POST_URL = BASE_URL + "companies/{id}/shares?format{format}";
	
	public static final String PRODUCT_FIELDS="(id,name,type,creation-timestamp,logo-url,description,features,video:(title,url),product-deal:(title,url,text),sales-persons,num-recommendations,recommendations:(recommender,id,product-id,text,reply,timestamp,likes:(timestamp,person)),product-category,website-url,disclaimer)";
	public static final String PRODUCTS_URL = BASE_URL + "companies/{id}/products:" + PRODUCT_FIELDS +"?start={start}&count={count}";
	
}
