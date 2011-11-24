package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * IM (Instance Message) Account Details for  Profile on LinkedIn
 *  
 * @author Robert Drysdale
 *
 */
public class ImAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String imAccountType;
	private final String imAccountName;

	public ImAccount(String imAccountType, String imAccountName) {
		this.imAccountType = imAccountType;
		this.imAccountName = imAccountName;
	}
	
	public String getImAccountType() {
		return imAccountType;
	}
	
	public String getImAccountName() {
		return imAccountName;
	}
}
