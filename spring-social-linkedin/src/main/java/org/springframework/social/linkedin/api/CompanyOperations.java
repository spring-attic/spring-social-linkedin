package org.springframework.social.linkedin.api;

import java.util.List;

public interface CompanyOperations {
	Company getCompany(int id);
	
	Company getCompanyByUniversalName(String name);
	
	List<Company> getCompaniesByEmailDomain(String domain);
	
	public SearchResultCompany search(String keywords);
	
	public List<Company> getFollowing();
	
	public List<Company> getSuggestionsToFollow();
	
	public void startFollowingCompany(int id);
	
	public void stopFollowingCompany(int id);
	
	public ProductResult getProducts(int companyId, int start, int count);
}
