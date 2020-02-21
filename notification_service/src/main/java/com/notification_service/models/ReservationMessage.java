package com.notification_service.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationMessage {
    private List<Integer> targets = new ArrayList<Integer>();
    private String message;
	public List<Integer> getTargets() {
		return targets;
	}
	public void setTargets(List<Integer> targets) {
		this.targets= targets;
	}
	public String getMessage() {
		return message;
	} 
	public void setMessage(String message) {
		this.message = message;
	}
	public ReservationMessage() {
		
	}
    
}
