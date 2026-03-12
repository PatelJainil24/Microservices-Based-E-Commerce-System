package com.javaexpress.user.dto;


public class CredentialDto {

	private Integer credentialId;
	private String username;
	private String password;
	private RoleBasedAuthority roleBasedAuthority;
	
	public CredentialDto() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RoleBasedAuthority getRoleBasedAuthority() {
		return roleBasedAuthority;
	}
	public void setRoleBasedAuthority(RoleBasedAuthority roleBasedAuthority) {
		this.roleBasedAuthority = roleBasedAuthority;
	}
	
	
}
