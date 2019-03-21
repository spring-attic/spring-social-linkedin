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
package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.List;

/**
 * Model class representing a Company
 * 
 * @author Robert Drysdale
 */
public class Company extends LinkedInObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id;
	
	private final String name;
	
	private String industry;
	
	private String size;
	
	private String type;
	
	private String blogRssUrl;
	
	private CodeAndName companyType;
	
	private String description;
	
	private List<String> emailDomains;
	
	private CodeAndName employeeCountRange;
	
	private int foundedYear;
	
	private List<CompanyLocation> locations;
	
	private String logoUrl;
	
	private int numFollowers;
	
	private List<String> specialties;
	
	private String squareLogoUrl;
	
	private CodeAndName status;
	
	private CodeAndName stockExchange;
	
	private String ticker;
	
	private String twitterId;
	
	private String universalName;
	
	private String websiteUrl;
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String getIndustry() {
		return industry;
	}
	
	public String getSize() {
		return size;
	}
	
	public String getType() {
		return type;
	}
	
	public List<String> getEmailDomains() {
		return emailDomains;
	}
	
	public String getBlogRssUrl() {
		return blogRssUrl;
	}
	
	public CodeAndName getCompanyType() {
		return companyType;
	}

	public String getDescription() {
		return description;
	}

	public CodeAndName getEmployeeCountRange() {
		return employeeCountRange;
	}

	public int getFoundedYear() {
		return foundedYear;
	}

	public List<CompanyLocation> getLocations() {
		return locations;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public int getNumFollowers() {
		return numFollowers;
	}

	public List<String> getSpecialties() {
		return specialties;
	}

	public String getSquareLogoUrl() {
		return squareLogoUrl;
	}

	public CodeAndName getStatus() {
		return status;
	}

	public CodeAndName getStockExchange() {
		return stockExchange;
	}

	public String getTicker() {
		return ticker;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public String getUniversalName() {
		return universalName;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}
	
	public static final class CompanyLocation extends LinkedInObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final CompanyAddress address;
		private final CompanyContactInfo contactInfo;
		
		public CompanyLocation(CompanyAddress address, CompanyContactInfo contactInfo) {
			this.address = address;
			this.contactInfo = contactInfo;
		}
		
		public CompanyAddress getAddress() {
			return address;
		}
		
		public CompanyContactInfo getContactInfo() {
			return contactInfo;
		}
	}
	
	public static final class CompanyAddress extends LinkedInObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final String city;
		private final String postalCode;
		private final String street1;
		
		public CompanyAddress(String city, String postalCode, String street1) {
			this.city = city;
			this.postalCode = postalCode;
			this.street1 = street1;
		}
		
		public String getCity() {
			return city;
		}
		
		public String getPostalCode() {
			return postalCode;
		}
		
		public String getStreet1() {
			return street1;
		}
	}
	
	public static final class CompanyContactInfo extends LinkedInObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final String fax;
		private final String phone1;
		
		public CompanyContactInfo(String fax, String phone1) {
			this.fax = fax;
			this.phone1 = phone1;
		}
		
		public String getFax() {
			return fax;
		}
		
		public String getPhone1() {
			return phone1;
		}
	}
}
