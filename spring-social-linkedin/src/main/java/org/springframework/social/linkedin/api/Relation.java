package org.springframework.social.linkedin.api;

import java.io.Serializable;

public class Relation implements Serializable {
	private static final long serialVersionUID = -3045269758643542427L;
	
	private final int distance;

	public Relation(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}
}
