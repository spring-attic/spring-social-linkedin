package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Product
 * 
 * @author Robert Drysdale
 *
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Date creationTimestamp;
	private final String description;
	private final List<String> features;
	private final int id;
	private final String logoUrl;
	private final String name;
	private final int numRecommendations;
	private final CodeAndName productCategory;
	private final List<ProductRecommendation> recommendations;
	private final CodeAndName type;
	private final String websiteUrl;
	
	public Product(Date creationTimestamp, String description,
			List<String> features, int id, String logoUrl, String name,
			int numRecommendations, CodeAndName productCategory,
			List<ProductRecommendation> recommendations,
			CodeAndName type, String websiteUrl) {
		this.creationTimestamp = creationTimestamp;
		this.description = description;
		this.features = features;
		this.id = id;
		this.logoUrl = logoUrl;
		this.name = name;
		this.numRecommendations = numRecommendations;
		this.productCategory = productCategory;
		this.recommendations = recommendations;
		this.type = type;
		this.websiteUrl = websiteUrl;
	}
	
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getFeatures() {
		return features;
	}

	public int getId() {
		return id;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public String getName() {
		return name;
	}

	public int getNumRecommendations() {
		return numRecommendations;
	}

	public CodeAndName getProductCategory() {
		return productCategory;
	}
	
	public List<ProductRecommendation> getRecommendations() {
		return recommendations;
	}

	public CodeAndName getType() {
		return type;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}
	
	public static final class ProductRecommendation implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private final int id;
		private final int productId;
		private LinkedInProfile recommender;
		private final String text;
		private Date timestamp;
		
		public ProductRecommendation(int id, int productId,
				LinkedInProfile recommender, String text, Date timestamp) {
			this.id = id;
			this.productId = productId;
			this.recommender = recommender;
			this.text = text;
			this.timestamp = timestamp;
		}
		
		public int getId() {
			return id;
		}
		
		public int getProductId() {
			return productId;
		}
		
		public LinkedInProfile getRecommender() {
			return recommender;
		}
		
		public String getText() {
			return text;
		}
		
		public Date getTimestamp() {
			return timestamp;
		}
	}
}
