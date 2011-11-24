package org.springframework.social.linkedin.api;

import java.io.Serializable;

/**
 * Phone Number
 * 
 * @author Robert Drysdale
 *
 */
public class PhoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String phoneType;
	private final String phoneNumber;

	public PhoneNumber(String phoneType, String phoneNumber) {
		this.phoneType = phoneType;
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneType() {
		return phoneType;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
