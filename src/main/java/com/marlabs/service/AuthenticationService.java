package com.marlabs.service;

import java.security.NoSuchAlgorithmException;

public interface AuthenticationService {

	public abstract String getAuthenticationQuery(String jwtToken) throws NoSuchAlgorithmException;
	
	public abstract boolean validateAuthenticationQuery(String jwtToken, String query, int sum);
}
