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

/**
 * Contains LinkedIn structure which contains a code and a name for that code.
 * The LinkedIn API returns a code/name pair for several of its fields (e.g., product category, company type, etc).
 * This class generically captures the code/name pair for a field.
 * @author Robert Drysdale
 */
public class CodeAndName extends LinkedInObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String code;
	
	private final String name;
	
	public CodeAndName(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
}
