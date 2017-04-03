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
package org.springframework.social.linkedin.api;

import java.util.List;

/**
 * Operations related to Companies on LinkedIn
 * 
 * @author Robert Drysdale
 */
public interface CompanyOperations {

	/**
	 * Retrieve Company Details based on unique integer id
	 * @param id the company ID
	 * @return company
	 */
	Company getCompany(int id);
	
	/**
	 * Retrieve Company Details based on unique name id
	 * @param name the company name
	 * @return company
	 */
	Company getCompanyByUniversalName(String name);
	
	/**
	 * Retrive List of Company Details based on email domain
	 * 
	 * @param domain Email Domain
	 * @return List of Companies
	 */
	List<Company> getCompaniesByEmailDomain(String domain);
	
	/**
	 * Search of Companies based on space separated list of keywords
	 * 
	 * @param keywords keywords to search with
	 * @return Search Result with count, start, total and list of companies
	 */
	Companies search(String keywords);
	
	/**
	 * Retrieve list of Companies that user is following
	 * @return List of Companies
	 */
	List<Company> getFollowing();
	
	/**
	 * Retrieve a list of Suggested Companies for user to follow
	 * @return List of Companies
	 */
	List<Company> getSuggestionsToFollow();
	
	/**
	 * Start following company
	 * @param id the company ID
	 */
	void startFollowingCompany(int id);
	
	/**
	 * Stop following company
	 * @param id the company ID
	 */
	void stopFollowingCompany(int id);
	
	/**
	 * Get products for a company.
	 * @param companyId the ID of the company to get products for.
	 * @param start The starting point in the result set. Used with count for pagination.
	 * @param count The number of products to return. Used with start for pagination.
	 * @return the products for the specified company.
	 */
	Products getProducts(int companyId, int start, int count);

	/**
	 * Get a list of all organizational accounts
	 * @return company list
	 */
	Companies getCompanies();

}
