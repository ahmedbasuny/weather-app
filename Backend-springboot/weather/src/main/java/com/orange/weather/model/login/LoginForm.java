package com.orange.weather.model.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginForm {

    @NotBlank
    @Size(max = 50)
	@Email
	private String mail;
	
    @NotBlank
    @Size(min = 6, max = 40)
	private String password;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passworrd) {
		this.password = passworrd;
	}
    
	@Override
	public String toString() {
		return "mail : " + getMail();
	}
    
}
