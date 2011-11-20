package org.springframework.social.linkedin.api;

import java.util.List;


public class ProductResult extends SearchResult {

	private static final long serialVersionUID = 1L;
	
	private List<Product> products;
	
	public ProductResult(int count, int start, int total) {
		super(count, start, total);
	}
	
	public List<Product> getProducts() {
		return products;
	}

}
