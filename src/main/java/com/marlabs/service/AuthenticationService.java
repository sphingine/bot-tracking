package com.marlabs.service;

public interface AuthenticationService {

	public abstract String getAuthenticationQuery(String jwtToken);
	
	public abstract boolean validateAuthenticationQuery(String jwtToken, String query, int sum);
}
