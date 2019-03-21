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

/**
 * Model class representing a location of a place that a user may check into in Facebook Places.
 * @author Craig Walls
 */
public class Location extends FacebookObject {

	private String id;
	
	private double latitude;

	private double longitude;

	private String street;

	private String city;

	private String state;

	private String country;

	private String zip;

	private String description;
	
	private String locatedIn;
	
	private String name;
	
	private String region;
	
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Location(String description) {
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getZip() {
		return zip;
	}

	public String getDescription() {
		return description;
	}
	
	public String getLocatedIn() {
		return locatedIn;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRegion() {
		return region;
	}
	
}
