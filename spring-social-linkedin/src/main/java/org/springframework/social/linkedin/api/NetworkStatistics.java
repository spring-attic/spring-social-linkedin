package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class NetworkStatistics implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int firstDegreeCount;
	private final int secondDegreeCount;
	
	public NetworkStatistics(int[] counts) {
		this.firstDegreeCount = counts[0];
		this.secondDegreeCount = counts[1];
	}
	
	public NetworkStatistics(int firstDegreeCount, int secondDegreeCount) {
		this.firstDegreeCount = firstDegreeCount;
		this.secondDegreeCount = secondDegreeCount;
	}
	
	public int getFirstDegreeCount() {
		return firstDegreeCount;
	}
	
	public int getSecondDegreeCount() {
		return secondDegreeCount;
	}
}
