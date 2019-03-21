/*
 * Copyright 2015 the original author or authors.
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
package org.springframework.social.facebook.api;

import java.io.Serializable;

/**
 * A simple reference to another Facebook object without the complete set of object data.
 * @author Craig Walls
 */
@SuppressWarnings("serial")
public class Reference extends FacebookObject implements Serializable {

	private final String id;

	private final String name;

    	@SuppressWarnings("unused")
    	private Reference() {
        	this(null, null);
    	}

    	public Reference(String id) {
		this(id, null);
	}

	public Reference(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
