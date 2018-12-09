package com.orange.weather.model.login;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;


public class SignUpForm {
	
	@NotBlank
    @Size(min=3, max = 50)
	private String name;
	
	@NaturalId
    @NotBlank
    @Size(max = 50)
	@Email
	private String mail;
	
	@NotBlank
    @Size(min=6, max = 100)
	private String password;
	
	@Pattern(regexp="^\\+?[0-9]\\d{1,14}$")
	private String phone;
	
	private Set<String> role;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<String> getRole() {
	   	return this.role;
    }
	    
	public void setRole(Set<String> role) {
	    this.role = role;
	}
	
	@Override
	public String toString() {
		return " user name" + getName() + ".mail : " + getMail()+ ".phone : " + getPhone() + " roles : {" + getRole().stream() + " }";
	}
}
