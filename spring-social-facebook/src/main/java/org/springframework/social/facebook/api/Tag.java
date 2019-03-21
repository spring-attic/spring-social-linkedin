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

import java.util.Date;

/**
 * Model class representing a tagged user in a video, photo, or checkin. 
 * @author Craig Walls
 */
public class Tag extends FacebookObject {
	private final String id;

	private final String name;
	
	private final Integer x;
	
	private final Integer y;
	
	private final Date createdTime;
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public Tag(String id, String name, Integer x, Integer y, Date createdTime) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.createdTime = createdTime;			
	}
}
