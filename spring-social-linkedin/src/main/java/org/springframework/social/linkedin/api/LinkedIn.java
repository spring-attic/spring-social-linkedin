/*
 * Copyright 2010 the original author or authors.
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

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.social.ApiBinding;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;

/**
 * <p>
 * Interface specifying a basic set of operations for interacting with LinkedIn.
 * Implemented by {@link LinkedInTemplate}.
 * </p>
 * 
 * <p>
 * Many of the methods contained in this interface require OAuth authentication
 * with LinkedIn. When a method's description speaks of the "current user", it
 * is referring to the user for whom the access token has been issued.
 * </p>
 * 
 * @author Craig Walls
 * @author Robert Drysdale
 */
public interface LinkedIn extends ApiBinding {
	/**
	 * Retrieves the user's LinkedIn profile ID.
	 * 
	 * @return the user's LinkedIn profile ID.
	 */
	String getProfileId();

	/**
	 * Retrieves a URL to the user's public profile page.
	 * 
	 * @return a URL to the user's public profile page.
	 */
	String getProfileUrl();

	/**
	 * Retrieves the current user's profile details.
	 * 
	 * @return the user's profile data.
	 */
	LinkedInProfile getUserProfile();
	

	/**
	 * Retrieves the 1st-degree connections from the current user's network.
	 * 
	 * @return the user's connections
	 */
	List<LinkedInProfile> getConnections();
	
	/**
	 * Retrieves network updates for connections
	 * 
	 * @return network updates
	 */
	public List<LinkedInNetworkUpdate> getNetworkUpdates();
	
	/**
	 * Retrieves network updates for connections
	 * 
	 * Can iterate through all records using start and count
	 * 
	 * @param recordStart First record to start at
	 * @param recordCount Number of records to return
	 * @return network updates
	 */
	public List<LinkedInNetworkUpdate> getNetworkUpdates(int recordStart, int recordCount);
	
	/**
	 * Advanced retrieval of network updates for connections or self
	 * 
	 * {@see LinkedInParameters} for details of each field.  Many are left
	 * as null to turn off.
	 * 
	 * As an example.  To retrieve last 20 SHAR Updates (Shares) for a particular user:
	 * 
	 * LinkedInParameters parameters = new LinkedInParameters(
	 * 		"VALID_ID",
	 * 		true,
	 * 		0,
	 * 		20,
	 * 		null,
	 * 		null,
	 * 		false,
	 * 		false,
	 * 		Collections.<UpdateTypeInput>singletonList(UpdateTypeInput.SHAR));
	 * 	List<LinkedInNetworkUpdate> updates = linkedIn.getNetworkUpdates(parameters);
	 * 
	 * @param parameters LinkedInParameters
	 * @return network updates
	 */
	public List<LinkedInNetworkUpdate> getNetworkUpdates(LinkedInParameters parameters);
	
	/**
	 * Advanced retrieval of network updates for connections or self
	 * in JSON format rather than object.  Useful for debugging.
	 * 
	 * {@see getNetworkUpdates(LinkedInParameters parameters)}
	 * 
	 * 
	 * @param parameters LinkedInParameters
	 * @return network updates
	 */
	public String getNetworkUpdatesJson(LinkedInParameters parameters);
	
	/**
	 * Retrieves json data from provided url
	 * url must be valid linked in url.
	 * Usefult for debugging
	 * 
	 * @param url
	 * @return JSON String data
	 */
	public String getJson(String url);
	
	/**
	 * Get list of comments on a Network Update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 * @return
	 */
	public List<Comment> getNetworkUpdateComments(String updateKey);
	
	/**
	 * Get list of likes on a Network Update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 * @return
	 */
	public List<LinkedInProfile> getNetworkUpdateLikes(String updateKey);
	
	/**
	 * Like network update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 */
	public void likeNetworkUpdate(String updateKey);
	
	/**
	 * Unlike network update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 */
	public void unlikeNetworkUpdate(String updateKey);
	
	/**
	 * Comment on a network update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 * @param comment
	 */
	public void commentOnNetworkUpdate(String updateKey, String comment);
	
	/**
	 * Get the last share for the current user
	 * 
	 * @return CurrentShare
	 */
	public CurrentShare getCurrentShare();
	
	/**
	 * Share something to network
	 * 
	 * @param share
	 * @return URI to share location
	 */
	public URI share(NewShare share);
	
	/**
	 * <p>
	 * Model Object passed to getNetworkUpdates()
	 * to control what parameters are set on the Http GET
	 * request to LinkedIn Network Updates API
	 * </p>
	 * @author Robert Drysdale
	 *
	 */
	public static class LinkedInParameters {
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
		public LinkedInParameters(String user, boolean self, int recordStart, int recordCount, 
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
		public LinkedInParameters(String user, boolean self, int recordStart, int recordCount, 
				Date recordsBefore, Date recordsAfter, boolean updateAll, boolean showHidden, 
				UpdateTypeInput... updateTypes) {
			this(user, self, recordStart, recordCount, recordsBefore, recordsAfter, updateAll,
					showHidden, Arrays.asList(updateTypes));
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
}
