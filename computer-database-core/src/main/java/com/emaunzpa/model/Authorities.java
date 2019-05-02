package com.emaunzpa.model;

public class Authorities {

	private Integer id;
	private String authority;
	private User user;
	
	public Authorities() {
		
	}
	
	public Authorities(String authority, User user) {
		this.authority = authority;
		this.user = user;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
