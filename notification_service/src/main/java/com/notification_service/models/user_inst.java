package com.notification_service.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_inst")
public class user_inst {
	@Id
	private int id;
	private String role;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public user_inst(int id, String role, String email) {
		super();
		this.id = id;
		this.role = role;
		this.email = email;
	}
	public user_inst() {
		// TODO Auto-generated constructor stub
	}

}
