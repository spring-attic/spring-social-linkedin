package org.springframework.social.linkedin.api;

import java.util.List;

/**
 *	Operations related to Companies on LinkedIn
 * 
 * @author Robert Drysdale
 *
 */
public interface CompanyOperations {
	/**
	 * Retrieve Company Details based on unique integer id
	 * @param id
	 * @return company
	 */
	Company getCompany(int id);
	
	/**
	 * Retrieve Company Details based on unique name id
	 * @param name
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
	 * @param keywords
	 * @return Search Result with count, start, total and list of companies
	 */
	SearchResultCompany search(String keywords);
	
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
	 * @param id
	 */
	void startFollowingCompany(int id);
	
	/**
	 * Stop following company
	 * @param id
	 */
	void stopFollowingCompany(int id);
	
	ProductResult getProducts(int companyId, int start, int count);
}
