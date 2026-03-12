package com.javaexpress.user.dto;

import lombok.Getter;
import lombok.Setter;


public class UserDto {

	private Integer userId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String contact;
	
	private CredentialDto credential;
	
	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public CredentialDto getCredential() {
		return credential;
	}

	public void setCredential(CredentialDto credential) {
		this.credential = credential;
	}
	
	
}
