package com.circulation_service.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="circulation")
public class Circulation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	int userId;
	int bookId;
	Date date;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	 
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
	
	
}
