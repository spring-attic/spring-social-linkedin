package org.springframework.social.linkedin.api;

import java.net.URI;
import java.util.List;

public interface NetworkUpdateOperations {
	/**
	 * Retrieves network updates for connections
	 * 
	 * @return network updates
	 */
	List<LinkedInNetworkUpdate> getNetworkUpdates();
	
	/**
	 * Retrieves network updates for connections
	 * 
	 * Can iterate through all records using start and count
	 * 
	 * @param recordStart First record to start at
	 * @param recordCount Number of records to return
	 * @return network updates
	 */
	List<LinkedInNetworkUpdate> getNetworkUpdates(int recordStart, int recordCount);
	
	/**
	 * Advanced retrieval of network updates for connections or self
	 * 
	 * {link NetworkUpdateParameters} for details of each field.  Many are left
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
	List<LinkedInNetworkUpdate> getNetworkUpdates(NetworkUpdateParameters parameters);
	
	/**
	 * Advanced retrieval of network updates for connections or self
	 * in JSON format rather than object.  Useful for debugging.
	 * 
	 * {@link #getNetworkUpdates(NetworkUpdateParameters parameters)}
	 * 
	 * 
	 * @param parameters LinkedInParameters
	 * @return network updates
	 */
	String getNetworkUpdatesJson(NetworkUpdateParameters parameters);
	
	/**
	 * Get list of comments on a Network Update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 * @return List of Comments
	 */
	List<Comment> getNetworkUpdateComments(String updateKey);
	
	/**
	 * Get list of likes on a Network Update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 * @return List of Linked In Profiles
	 */
	List<LinkedInProfile> getNetworkUpdateLikes(String updateKey);
	
	/**
	 * Updates status of user
	 * 
	 * @param update Text of update
	 */
	void createNetworkUpdate(String update);
	
	/**
	 * Like network update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 */
	void likeNetworkUpdate(String updateKey);
	
	/**
	 * Unlike network update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 */
	void unlikeNetworkUpdate(String updateKey);
	
	/**
	 * Comment on a network update.
	 * updateKey must be a valid key from linkedIn.
	 * e.g UNIU-2481200-5541820536390100000-SHARE
	 * 
	 * @param updateKey
	 * @param comment
	 */
	void commentOnNetworkUpdate(String updateKey, String comment);
	
	/**
	 * Get the last share for the current user
	 * 
	 * @return CurrentShare
	 */
	CurrentShare getCurrentShare();
	
	/**
	 * Share something to network
	 * 
	 * @param share
	 * @return URI to share location
	 */
	URI share(NewShare share);
}
