package com.infosys.infytel.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDetails {
	@NotEmpty(message="Please enter email")
	private String email;
	@NotEmpty(message="Please enter password")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
