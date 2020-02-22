package com.PaymentService.model;

import java.util.Date;

public class ReturnMessage {
	int userId;
	int bookId;
	Date date;
	//String cause;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
//	public String getCause() {
//		return cause;
//	}
//	public void setCause(String cause) {
//		this.cause = cause;
//	}
//	

}
