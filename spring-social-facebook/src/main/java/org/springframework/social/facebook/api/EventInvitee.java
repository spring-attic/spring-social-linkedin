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
 * Model class representing someone who has been invited to an event.
 * @author Craig Walls
 */
public class EventInvitee extends FacebookObject {
	
	private String id;
	
	private String name;
	
	private RsvpStatus rsvpStatus;
	
	/**
	 * @return the invitee's user ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the invitee's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the invitee's RSVP status (attending, unsure, not-replied, or declined).
	 */
	public RsvpStatus getRsvpStatus() {
		return rsvpStatus;
	}

}
