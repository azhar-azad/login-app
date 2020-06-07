package com.dsi.codingtest.loginapp.ui.models.request;

public class UserLoginRequestModel {

	private String email;
	private String password;

	public UserLoginRequestModel() {
		super();
	}

	public UserLoginRequestModel(String email, String password) {
		this.email = email;
		this.password = password;
	}

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
