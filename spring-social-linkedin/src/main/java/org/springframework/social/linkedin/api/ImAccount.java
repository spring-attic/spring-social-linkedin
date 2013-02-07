/*
 * Copyright 2013 the original author or authors.
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

import java.io.Serializable;

/**
 * Model class representing IM (Instance Message) Account Details for a Profile on LinkedIn
 *  
 * @author Robert Drysdale
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
