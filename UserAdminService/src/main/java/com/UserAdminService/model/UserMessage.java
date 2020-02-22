package com.UserAdminService.model;

public class UserMessage {

	int id;
	String message;
	
	public UserMessage(int id, String message) {
		this.id = id;
		this.message = message;
	}
	public UserMessage() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
