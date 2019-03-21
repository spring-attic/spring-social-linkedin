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

import org.springframework.social.OperationNotPermittedException;

/**
 * Exception thrown when attempting to perform operation on a resource that must be owned by the authenticated user,
 * but is not. For example, attempting to delete someone else's friendlist.
 * @author Craig Walls
 */
@SuppressWarnings("serial")
public class ResourceOwnershipException extends OperationNotPermittedException {

	public ResourceOwnershipException(String message) {
		super("facebook", message);
	}

}
