package com.BookAdminService.model;

public class BookMessage {

	int id;
	String message;
	
	public BookMessage(int id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public BookMessage() {
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
