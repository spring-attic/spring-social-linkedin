package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.List;

/**
 * Details of position of a Job
 * 
 * @author Robert Drysdale
 *
 */
public class JobPosition implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String title;
	private final Location location;
	private CodeAndName experienceLevel;
	private List<CodeAndName> industries;
	private List<CodeAndName> jobFunctions;
	private CodeAndName jobType;
	
	public JobPosition(String title, Location location) {
		this.title = title;
		this.location = location;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Location getLocation() {
		return location;
	}

	public CodeAndName getExperienceLevel() {
		return experienceLevel;
	}

	public List<CodeAndName> getIndustries() {
		return industries;
	}

	public List<CodeAndName> getJobFunctions() {
		return jobFunctions;
	}
	
	public CodeAndName getJobType() {
		return jobType;
	}
}
