package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Job Bookmark
 * 
 * @author Robert Drysdale
 *
 */
public class JobBookmark implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final boolean isApplied;
	private final boolean isSaved;
	private final Job job;
	private final Date savedTimestamp;
	
	public JobBookmark(boolean isApplied, boolean isSaved, Job job, Date savedTimestamp) {
		this.isApplied = isApplied;
		this.isSaved = isSaved;
		this.job = job;
		this.savedTimestamp = savedTimestamp;
	}

	public boolean isApplied() {
		return isApplied;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public Job getJob() {
		return job;
	}

	public Date getSavedTimestamp() {
		return savedTimestamp;
	}
}
