package com.reservation_service.models;

import java.util.List;

public class ReservationMessage {
	
	List<Integer> targets;
	
	String message;
	
	public ReservationMessage() {
		
	}

	public ReservationMessage(List<Integer> targets, String message) {
		this.targets = targets;
		this.message = message;
	}

	public List<Integer> getTargets() {
		return targets;
	}

	public void setTargets(List<Integer> targets) {
		this.targets = targets;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
