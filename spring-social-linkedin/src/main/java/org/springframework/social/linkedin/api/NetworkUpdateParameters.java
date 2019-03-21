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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Model Object passed to getNetworkUpdates() to control what parameters are set on the HTTP GET request to LinkedIn Network Updates API.
 * 
 * @author Robert Drysdale
 */
public class NetworkUpdateParameters {
	
	private final String user;

	private final boolean self;
	
	private final int recordStart;
	
	private final int recordCount;
	
	private final Date recordsBefore;
	
	private final Date recordsAfter;
	
	private final List<UpdateTypeInput> updateTypes;
	
	private final boolean updateAll;
	
	private final boolean showHidden;
	
	/**
	 * 
	 * @param user User to retrieve updates for (Set to null to retrieve for current user)
	 * @param self Show updates by self (Set to false to retrieve connections updates)
	 * @param recordStart First update to retrieve (Use with recordCount to iterate through updates)
	 * @param recordCount Number of updates to retrieve (Use with recordCount to interate through updates)
	 * @param recordsBefore Retrieve records before this Date (Set to null to not use)
	 * @param recordsAfter Retrieve records after this Date (Set to null to not use)
	 * @param updateAll Shortcut for All UpdateTypes (overrides updateTypes if set)
	 * @param showHidden Show updates that user has set to hidden
	 * @param updateTypes List of Update Types to retrieve
	 */
	public NetworkUpdateParameters(String user, boolean self, int recordStart, int recordCount, 
			Date recordsBefore, Date recordsAfter, boolean updateAll, boolean showHidden, 
			List<UpdateTypeInput> updateTypes) {
		this.user = user;
		this.self = self;
		this.recordStart = recordStart;
		this.recordCount = recordCount;
		this.recordsBefore = recordsBefore;
		this.recordsAfter = recordsAfter;
		this.updateTypes = updateTypes;
		this.updateAll = updateAll;
		this.showHidden = showHidden;
	}
	
	/**
	 * 
	 * @param user User to retrieve updates for (Set to null to retrieve for current user)
	 * @param self Show updates by self (Set to false to retrieve connections updates)
	 * @param recordStart First update to retrieve (Use with recordCount to iterate through updates)
	 * @param recordCount Number of updates to retrieve (Use with recordCount to interate through updates)
	 * @param recordsBefore Retrieve records before this Date (Set to null to not use)
	 * @param recordsAfter Retrieve records after this Date (Set to null to not use)
	 * @param updateAll Shortcut for All UpdateTypes (overrides updateTypes if set)
	 * @param showHidden Show updates that user has set to hidden
	 * @param updateTypes List of Update Types to retrieve
	 */
	public NetworkUpdateParameters(String user, boolean self, int recordStart, int recordCount, Date recordsBefore, Date recordsAfter, boolean updateAll, boolean showHidden, UpdateTypeInput... updateTypes) {
		this(user, self, recordStart, recordCount, recordsBefore, recordsAfter, updateAll, showHidden, Arrays.asList(updateTypes));
	}
	
	public String getUser() {
		return user;
	}
	
	public boolean getSelf() {
		return self;
	}
	
	public int getRecordStart() {
		return recordStart;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public Date getRecordsBefore() {
		return recordsBefore;
	}
	
	public Date getRecordsAfter() {
		return recordsAfter;
	}
	
	public List<UpdateTypeInput> getUpdateTypes() {
		return updateTypes;
	}
	
	public boolean getUpdateAll() {
		return updateAll;
	}
	
	public boolean getShowHidden() {
		return showHidden;
	}

}
